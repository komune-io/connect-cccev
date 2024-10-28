package cccev.f2.requirement

import cccev.f2.requirement.command.RequirementAddConceptsFunction
import cccev.f2.requirement.command.RequirementAddEvidenceTypesFunction
import cccev.f2.requirement.command.RequirementAddRequirementsFunction
import cccev.f2.requirement.command.RequirementCreateFunction
import cccev.f2.requirement.command.RequirementRemoveConceptsFunction
import cccev.f2.requirement.command.RequirementRemoveEvidenceTypesFunction
import cccev.f2.requirement.command.RequirementRemoveRequirementsFunction
import cccev.f2.requirement.command.RequirementUpdateFunction
import cccev.f2.requirement.query.RequirementGetByIdentifierFunction
import cccev.f2.requirement.query.RequirementGetFunction

/**
 * @d2 api
 * @parent [cccev.core.requirement.D2RequirementPage]
 */
interface RequirementApi: RequirementQueryApi, RequirementCommandApi

interface RequirementCommandApi {
    /** Create a requirement */
    fun requirementCreate(): RequirementCreateFunction

    /** Update a requirement */
    fun requirementUpdate(): RequirementUpdateFunction

    /** Add sub-requirements to a requirement */
    fun requirementAddRequirements(): RequirementAddRequirementsFunction

    /** Remove sub-requirements from a requirement */
    fun requirementRemoveRequirements(): RequirementRemoveRequirementsFunction

    /** Add information concepts to a requirement */
    fun requirementAddConcepts(): RequirementAddConceptsFunction

    /** Remove information concepts from a requirement */
    fun requirementRemoveConcepts(): RequirementRemoveConceptsFunction

    /** Add evidence types to a requirement */
    fun requirementAddEvidenceTypes(): RequirementAddEvidenceTypesFunction

    /** Remove evidence types from a requirement */
    fun requirementRemoveEvidenceTypes(): RequirementRemoveEvidenceTypesFunction
}

interface RequirementQueryApi {
    /** Get requirement **/
    fun requirementGet(): RequirementGetFunction
    fun requirementGetByIdentifier(): RequirementGetByIdentifierFunction
}
