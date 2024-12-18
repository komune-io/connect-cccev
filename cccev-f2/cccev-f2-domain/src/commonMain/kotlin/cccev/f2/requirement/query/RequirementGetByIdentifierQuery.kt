package cccev.f2.requirement.query

import cccev.dsl.model.RequirementIdentifier
import cccev.f2.CccevFlatGraph
import cccev.f2.CccevFlatGraphDTO
import cccev.f2.requirement.model.RequirementFlat
import cccev.f2.requirement.model.RequirementFlatDTO
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

typealias RequirementGetByIdentifierFunction = F2Function<RequirementGetByIdentifierQuery, RequirementGetByIdentifierResult>

@JsExport
interface RequirementGetByIdentifierQueryDTO {
    val identifier: RequirementIdentifier
}

@Serializable
data class RequirementGetByIdentifierQuery(
    override val identifier: RequirementIdentifier
): RequirementGetByIdentifierQueryDTO

@JsExport
interface RequirementGetByIdentifierResultDTO {
    val item: RequirementFlatDTO?
    val graph: CccevFlatGraphDTO
}

@Serializable
data class RequirementGetByIdentifierResult(
    override val item: RequirementFlat?,
    override val graph: CccevFlatGraph
): RequirementGetByIdentifierResultDTO
