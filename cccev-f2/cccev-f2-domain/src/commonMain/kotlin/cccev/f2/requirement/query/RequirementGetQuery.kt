package cccev.f2.requirement.query

import cccev.dsl.model.RequirementId
import cccev.f2.CccevFlatGraph
import cccev.f2.CccevFlatGraphDTO
import cccev.f2.requirement.model.RequirementFlat
import cccev.f2.requirement.model.RequirementFlatDTO
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

typealias RequirementGetFunction = F2Function<RequirementGetQuery, RequirementGetResult>

@JsExport
interface RequirementGetQueryDTO {
    val id: RequirementId
}

@Serializable
data class RequirementGetQuery(
    override val id: RequirementId
): RequirementGetQueryDTO

@JsExport
interface RequirementGetResultDTO {
    val item: RequirementFlatDTO?
    val graph: CccevFlatGraphDTO
}

@Serializable
data class RequirementGetResult(
    override val item: RequirementFlat?,
    override val graph: CccevFlatGraph
): RequirementGetResultDTO
