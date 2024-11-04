package cccev.core.certification.service

import cccev.commons.utils.mapAsync
import cccev.core.certification.entity.CertificationRepository
import cccev.core.certification.entity.RequirementCertificationEntity
import cccev.core.certification.entity.SupportedValueEntity
import cccev.core.certification.entity.isFulfilled
import cccev.core.concept.entity.InformationConceptEntity
import cccev.core.concept.entity.InformationConceptRepository
import cccev.dsl.model.CertificationId
import cccev.dsl.model.DataUnitType
import cccev.dsl.model.InformationConceptIdentifier
import cccev.dsl.model.RequirementCertificationId
import cccev.infra.neo4j.session
import cccev.infra.neo4j.transaction
import f2.spring.exception.NotFoundException
import java.util.UUID
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate
import org.neo4j.ogm.session.SessionFactory
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.StandardEvaluationContext
import org.springframework.stereotype.Service

@Service
class CertificationValuesFillerService(
    private val certificationRepository: CertificationRepository,
    private val informationConceptRepository: InformationConceptRepository,
    private val sessionFactory: SessionFactory
) {
    companion object {
        val spelParser = SpelExpressionParser()
    }

    /**
     * Add the given values to the relevant certifications,
     * then update the fulfillment indicators of said certifications,
     * then compute the values of auto-computable information concepts that depends on the given values,
     * and repeat the previous steps until everything is updated.
     */
    suspend fun fillValues(values: Map<InformationConceptIdentifier, String?>, context: Context) = sessionFactory.transaction { _, _->
        values.forEach { (informationConceptIdentifier, value) ->
            val requirementCertifications = certificationRepository.findRequirementCertificationsLinkedToInformationConcept(
                context.certificationId, context.rootRequirementCertificationId, informationConceptIdentifier
            )
            requirementCertifications.fillValue(informationConceptIdentifier, value)
        }

        certificationRepository.findAllSupportedValues(context.certificationId, context.rootRequirementCertificationId)
            .forEach { context.knownValues[it.concept.identifier] = it.value.convertTo(it.concept.unit.type) }

        computeValuesOfConsumersOf(values.keys, context)
    }

    private suspend fun List<RequirementCertificationEntity>.fillValue(
        informationConceptIdentifier: InformationConceptIdentifier, value: String?
    ) {
        println("fill value: [$informationConceptIdentifier]: [$value]")

        val informationConcept = informationConceptRepository.findByIdentifier(informationConceptIdentifier)
            ?: throw NotFoundException("InformationConcept", informationConceptIdentifier)

        // will throw if conversion is impossible
        value.convertTo(informationConcept.unit.type)

        val supportedValue = SupportedValueEntity().also { supportedValue ->
            supportedValue.id = UUID.randomUUID().toString()
            supportedValue.value = value
            supportedValue.concept = informationConcept
        }

        this.fillValue(informationConceptIdentifier, supportedValue)
    }

    private suspend fun List<RequirementCertificationEntity>.fillValue(
        informationConceptIdentifier: InformationConceptIdentifier, supportedValue: SupportedValueEntity
    ) = sessionFactory.session { session ->
        println("fill value: [$informationConceptIdentifier]: [$supportedValue]")
        this.onEach { requirementCertification ->
            val existingValue = requirementCertification.values.firstOrNull { it.concept.identifier == informationConceptIdentifier }

            if (existingValue == null) {
                requirementCertification.values.add(supportedValue)
            } else {
                existingValue.value = supportedValue.value
            }

            session.save(requirementCertification, 2)
        }.forEach { it.updateFulfillment() }

        certificationRepository.findSupportingEvidenceFor(supportedValue.id)?.let { evidence ->
            supportedValue.evidences.add(evidence)
            session.save(supportedValue, 1)
        }
    }

    private suspend fun computeValuesOfConsumersOf(
        informationConceptIdentifiers: Set<InformationConceptIdentifier>,
        context: Context
    ) {
        val consumers = informationConceptIdentifiers.mapAsync { informationConceptIdentifier ->
            informationConceptRepository.findDependingOn(informationConceptIdentifier)
        }.flatten()
            .distinctBy(InformationConceptEntity::identifier)

        consumers.computeValues(context)
    }

    private suspend fun List<InformationConceptEntity>.computeValues(context: Context) {
        val computableConcepts = this.filter { concept ->
            concept.expressionOfExpectedValue != null
                    && concept.dependencies.all { it.identifier in context.knownValues }
        }

        val expressionContext = StandardEvaluationContext().apply {
            setVariables(context.knownValues)
        }

        computableConcepts.forEach { concept ->
            println("compute value: [${concept.identifier}]")

            val certifications = certificationRepository.findRequirementCertificationsLinkedToInformationConcept(
                context.certificationId, context.rootRequirementCertificationId, concept.identifier
            )

            val value = spelParser.parseExpression(concept.expressionOfExpectedValue!!)
                .getValue(expressionContext, concept.unit.type.klass())
                .also { context.knownValues[concept.identifier] = it }
                .let {
                    SupportedValueEntity().also { supportedValue ->
                        supportedValue.id = UUID.randomUUID().toString()
                        supportedValue.value = it.toString()
                        supportedValue.concept = concept
                    }
                }

            certifications.fillValue(concept.identifier, value)
        }
    }

    private suspend fun RequirementCertificationEntity.updateFulfillment() {
        println("update fulfillment: [$id] (requirement: [${requirement.identifier}])")
        var changed: Boolean

        val mappedValues = values.associate {
            it.concept.identifier to it.value.convertTo(it.concept.unit.type)
        }

        hasAllValues = requirement.concepts.all { mappedValues[it.identifier] != null }
            .also { changed = it != hasAllValues }

        isEnabled = evaluateBooleanExpression(
            requirement.enablingCondition,
            requirement.enablingConditionDependencies.map(InformationConceptEntity::identifier).toSet(),
            mappedValues
        ).also { changed = changed || it != isEnabled }

        isValidated = evaluateBooleanExpression(
            requirement.validatingCondition,
            requirement.validatingConditionDependencies.map(InformationConceptEntity::identifier).toSet(),
            mappedValues
        ).also { changed = changed || it != isValidated }

        isFulfilled = isFulfilled()
            .also { changed = changed || it != isFulfilled }

        if (changed) {
            certificationRepository.save(this)
            certificationRepository.findParentsOf(id)
                .forEach { parent -> parent.updateFulfillment() }
        }
    }

    private suspend fun evaluateBooleanExpression(
        expression: String?,
        dependencies: Set<InformationConceptIdentifier>,
        values: Map<InformationConceptIdentifier, Any?>
    ): Boolean {
        if (expression == null) {
            return true
        }

        if (dependencies.any { it !in values }) {
            return false
        }

        val expressionContext = StandardEvaluationContext().apply {
            setVariables(dependencies.associateWith { values[it] })
        }

        return spelParser.parseExpression(expression)
            .getValue(expressionContext, Boolean::class.java)
            ?: false
    }

    private fun String?.convertTo(type: DataUnitType): Any? = when (type) {
        DataUnitType.BOOLEAN -> this?.toBoolean()
        DataUnitType.DATE -> this?.toLocalDate() // TODO define a standard type for DATE
        DataUnitType.NUMBER -> this?.toDouble()
        DataUnitType.STRING -> this
    }

    private fun DataUnitType.klass() = when (this) {
        DataUnitType.BOOLEAN -> Boolean::class.java
        DataUnitType.DATE -> LocalDate::class.java // TODO define a standard type for DATE
        DataUnitType.NUMBER -> Double::class.java
        DataUnitType.STRING -> String::class.java
    }

    data class Context(
        val certificationId: CertificationId,
        val rootRequirementCertificationId: RequirementCertificationId?,
        val knownValues: MutableMap<InformationConceptIdentifier, Any?> = mutableMapOf()
    )
}
