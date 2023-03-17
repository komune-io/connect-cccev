package cccev.projection.api.entity.request

import cccev.dsl.model.Evidence
import cccev.dsl.model.RequirementId
import cccev.dsl.model.SupportedValue
import cccev.projection.api.entity.EntityBase
import cccev.projection.api.entity.Relation
import cccev.s2.concept.domain.InformationConceptId
import cccev.s2.request.domain.RequestState
import cccev.s2.request.domain.model.RequestId
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship

@Node("Request")
class RequestEntity: EntityBase<RequestId, RequestState>() {
    lateinit var frameworkId: RequirementId
    @Relationship(type = Relation.PROVIDE_EVIDENCE)
    var evidences: MutableList<Evidence> = mutableListOf()
    var supportedValues: MutableMap<InformationConceptId, SupportedValue> = mutableMapOf()
}
