package cccev.dsl.dto.query

import ccev.dsl.core.Requirement
import ccev.dsl.core.RequirementId
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

typealias GetRequirementQueryFunction = F2Function<GetRequirementQuery, GetRequirementQueryResult>

@JsExport
@JsName("GetRequirementQueryDTO")
interface GetRequirementQueryDTO {
    val requirementId: RequirementId
}

class GetRequirementQuery(
    override val requirementId: RequirementId
): GetRequirementQueryDTO

@JsExport
@JsName("GetRequirementQueryResultDTO")
interface GetRequirementQueryResultDTO {
    val requirement: Requirement?
}

class GetRequirementQueryResult(
    override val requirement: Requirement?
): GetRequirementQueryResultDTO
