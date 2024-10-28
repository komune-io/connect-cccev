package cccev.test.f2.requirement.command

import cccev.f2.requirement.command.RequirementCreateCommand
import cccev.core.requirement.entity.RequirementRepository
import cccev.f2.requirement.model.RequirementKind
import cccev.f2.requirement.RequirementEndpoint
import cccev.test.CccevCucumberStepsDefinition
import cccev.test.f2.requirement.data.requirement
import f2.dsl.fnc.invokeWith
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import net.datafaker.Faker
import org.assertj.core.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.assertion.AssertionBdd
import s2.bdd.data.TestContextKey
import s2.bdd.data.parser.extract
import s2.bdd.data.parser.extractList

class RequirementCreateSteps: En, CccevCucumberStepsDefinition() {

    @Autowired
    private lateinit var requirementEndpoint: RequirementEndpoint

    @Autowired
    private lateinit var requirementRepository: RequirementRepository

    private lateinit var command: RequirementCreateCommand

    init {
        DataTableType(::requirementCreateParams)
        DataTableType(::requirementAssertParams)

        When("I create a requirement") {
            step {
                createRequirement(requirementCreateParams(null))
            }
        }

        When("I create a requirement:") { params: RequirementCreateParams ->
            step {
                createRequirement(params)
            }
        }

        Given("A requirement is created") {
            step {
                createRequirement(requirementCreateParams(null))
            }
        }

        Given("A requirement is created:") { params: RequirementCreateParams ->
            step {
                createRequirement(params)
            }
        }

        Given("Some requirements are created:") { dataTable: DataTable ->
            step {
                dataTable.asList(RequirementCreateParams::class.java)
                    .forEach { createRequirement(it) }
            }
        }

        Then("The requirement should be created") {
            step {
                val requirementId = context.requirementIds.lastUsed
                AssertionBdd.requirement(requirementRepository).assertThatId(requirementId).hasFields(
                    kind = command.kind,
                    name = command.name,
                    description = command.description,
                    type = command.type,
                    hasRequirement = command.subRequirementIds,
                    hasConcept = command.conceptIds,
                    hasEvidenceType = command.evidenceTypeIds,
                )
            }
        }

        Then("The requirement should be created:") { params: RequirementAssertParams ->
            step {
                val requirementId = context.requirementIds.safeGet(params.identifier)
                val requirement = requirementRepository.findById(requirementId)
                Assertions.assertThat(requirement).isNotNull

                AssertionBdd.requirement(requirementRepository).assertThat(requirement!!).hasFields(
                    kind = params.kind ?: requirement.kind,
                    name = params.name ?: requirement.name,
                    description = params.description ?: requirement.description,
                    type = params.type ?: requirement.type,
                    hasRequirement = params.hasRequirement?.map(context.requirementIds::safeGet)
                        ?: requirement.subRequirements.map { it.id },
                    hasConcept = params.hasConcept?.map(context.conceptIds::safeGet) ?: requirement.concepts.map { it.id },
                    hasEvidenceType = params.hasEvidenceType?.map(context.evidenceTypeIds::safeGet)
                        ?: requirement.evidenceTypes.map { it.id },
                )
            }
        }
    }

    private suspend fun createRequirement(params: RequirementCreateParams) = context.requirementIds.register(params.identifier) {
        command = RequirementCreateCommand(
            identifier = params.identifier,
            kind = params.kind,
            name = params.name,
            description = params.description,
            type = params.type,
            subRequirementIds = params.hasRequirement.map { context.requirementIds[it] ?: it },
            conceptIds = params.hasConcept.map { context.conceptIds[it] ?: it },
            evidenceTypeIds = params.hasEvidenceType.map { context.evidenceTypeIds[it] ?: it },
            validatingCondition = params.validatingCondition,
            validatingConditionDependencies = params.validatingConditionDependencies.map { context.conceptIds[it] ?: it },
            properties = mapOf("blblbl" to "ok")
        )
        command.invokeWith(requirementEndpoint.requirementCreate()).id
    }

    private fun requirementCreateParams(entry: Map<String, String>?): RequirementCreateParams {
        val fakerRestaurant = Faker().restaurant()
        return RequirementCreateParams(
            identifier = entry?.get("identifier").orRandom(),
            kind = entry?.extract<RequirementKind>("kind") ?: RequirementKind.INFORMATION,
            name = entry?.get("name") ?: fakerRestaurant.name(),
            description = entry?.get("description") ?: fakerRestaurant.description(),
            type = entry?.get("type") ?: fakerRestaurant.type(),
            hasRequirement = entry?.extractList<TestContextKey>("hasRequirement").orEmpty(),
            hasConcept = entry?.extractList<TestContextKey>("hasConcept").orEmpty(),
            hasEvidenceType = entry?.extractList<TestContextKey>("hasEvidenceType").orEmpty(),
            isRequirementOf = entry?.extractList<TestContextKey>("isRequirementOf").orEmpty(),
            hasQualifiedRelation = entry?.extractList<TestContextKey>("hasQualifiedRelation").orEmpty(),
            validatingCondition = entry?.get("validatingCondition"),
            validatingConditionDependencies = entry?.extractList("validatingConditionDependencies") ?: emptyList(),
        )
    }

    private data class RequirementCreateParams(
        val identifier: TestContextKey,
        val kind: RequirementKind,
        val name: String,
        val description: String,
        val type: String,
        val hasRequirement: List<TestContextKey>,
        val hasConcept: List<TestContextKey>,
        val hasEvidenceType: List<TestContextKey>,
        val isRequirementOf: List<TestContextKey>,
        val hasQualifiedRelation: List<TestContextKey>,
        val validatingCondition: String?,
        val validatingConditionDependencies: List<TestContextKey>
    )

    private fun requirementAssertParams(entry: Map<String, String>) = RequirementAssertParams(
        identifier = entry["identifier"] ?: context.conceptIds.lastUsedKey,
        kind = entry.extract("kind"),
        name = entry["name"],
        description = entry["description"],
        type = entry["type"],
        hasRequirement = entry.extractList("hasRequirement"),
        hasConcept = entry.extractList("hasConcept"),
        hasEvidenceType = entry.extractList("hasEvidenceType")
    )

    private data class RequirementAssertParams(
        val identifier: TestContextKey,
        val kind: RequirementKind?,
        val name: String?,
        val description: String?,
        val type: String?,
        val hasRequirement: List<TestContextKey>?,
        val hasConcept: List<TestContextKey>?,
        val hasEvidenceType: List<TestContextKey>?
    )
}
