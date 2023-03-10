package cccev.test.s2.requirement.command

import cccev.projection.api.entity.requirement.RequirementRepository
import cccev.s2.requirement.api.RequirementAggregateService
import cccev.s2.requirement.domain.RequirementState
import cccev.s2.requirement.domain.command.RequirementCreateCommand
import cccev.s2.requirement.domain.model.RequirementKind
import cccev.test.CccevCucumberStepsDefinition
import cccev.test.s2.requirement.data.extractRequirementKind
import cccev.test.s2.requirement.data.requirement
import fixers.bdd.assertion.AssertionBdd
import fixers.bdd.data.TestContextKey
import fixers.bdd.data.parser.extractList
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.assertj.core.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import java.util.UUID

class RequirementCreateSteps: En, CccevCucumberStepsDefinition() {

    @Autowired
    private lateinit var requirementAggregateService: RequirementAggregateService

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
                    status = RequirementState.CREATED,
                    kind = command.kind,
                    name = command.name,
                    description = command.description,
                    hasRequirement = command.hasRequirement,
                    hasConcept = command.hasConcept,
                    hasEvidenceTypeList = command.hasEvidenceTypeList,
                )
            }
        }

        Then("The requirement should be created:") { params: RequirementAssertParams ->
            step {
                val requirementId = context.requirementIds.safeGet(params.identifier)
                val requirement = requirementRepository.findById(requirementId).awaitSingleOrNull()
                Assertions.assertThat(requirement).isNotNull

                AssertionBdd.requirement(requirementRepository).assertThat(requirement!!).hasFields(
                    status = RequirementState.CREATED,
                    kind = params.kind ?: requirement.kind,
                    name = params.name ?: requirement.name,
                    description = params.description ?: requirement.description,
                    hasRequirement = params.hasRequirement?.map(context.requirementIds::safeGet)
                        ?: requirement.hasRequirement.map { it.id },
                    hasConcept = params.hasConcept?.map(context.conceptIds::safeGet) ?: requirement.hasConcept.map { it.id },
                    hasEvidenceTypeList = params.hasEvidenceTypeList?.map(context.evidenceTypeListIds::safeGet)
                        ?: requirement.hasEvidenceTypeList.map { it.id },
                )
            }
        }
    }

    private suspend fun createRequirement(params: RequirementCreateParams) = context.requirementIds.register(params.identifier) {
        command = RequirementCreateCommand(
            identifier = "${params.identifier}_${UUID.randomUUID()}",
            kind = params.kind,
            name = params.name,
            description = params.description,
            hasRequirement = params.hasRequirement.map { context.requirementIdentifiers[it] ?: it },
            hasQualifiedRelation = params.hasQualifiedRelation.map { context.requirementIdentifiers[it] ?: it },
            hasConcept = params.hasConcept.map { context.conceptIds[it] ?: it },
            hasEvidenceTypeList = params.hasEvidenceTypeList.map { context.evidenceTypeListIds[it] ?: it },
        )
        context.requirementIdentifiers[params.identifier] = command.identifier
        requirementAggregateService.create(command).id
    }

    private fun requirementCreateParams(entry: Map<String, String>?) = RequirementCreateParams(
        identifier = entry?.get("identifier").orRandom(),
        kind = entry?.extractRequirementKind("kind") ?: RequirementKind.INFORMATION,
        name = entry?.get("name").orRandom(),
        description = entry?.get("description").orRandom(),
        hasRequirement = entry?.extractList("hasRequirement").orEmpty(),
        hasConcept = entry?.extractList("hasConcept").orEmpty(),
        hasEvidenceTypeList = entry?.extractList("hasEvidenceTypeList").orEmpty(),
        isRequirementOf = entry?.extractList("isRequirementOf").orEmpty(),
        hasQualifiedRelation = entry?.extractList("hasQualifiedRelation").orEmpty(),
    )

    private data class RequirementCreateParams(
        val identifier: TestContextKey,
        val kind: RequirementKind,
        val name: String,
        val description: String,
        val hasRequirement: List<TestContextKey>,
        val hasConcept: List<TestContextKey>,
        val hasEvidenceTypeList: List<TestContextKey>,
        val isRequirementOf: List<TestContextKey>,
        val hasQualifiedRelation: List<TestContextKey>,
    )

    private fun requirementAssertParams(entry: Map<String, String>) = RequirementAssertParams(
        identifier = entry["identifier"] ?: context.conceptIds.lastUsedKey,
        kind = entry.extractRequirementKind("kind"),
        name = entry["name"],
        description = entry["description"],
        hasRequirement = entry.extractList("hasRequirement"),
        hasConcept = entry.extractList("hasConcept"),
        hasEvidenceTypeList = entry.extractList("hasEvidenceTypeList")
    )

    private data class RequirementAssertParams(
        val identifier: TestContextKey,
        val kind: RequirementKind?,
        val name: String?,
        val description: String?,
        val hasRequirement: List<TestContextKey>?,
        val hasConcept: List<TestContextKey>?,
        val hasEvidenceTypeList: List<TestContextKey>?
    )
}
