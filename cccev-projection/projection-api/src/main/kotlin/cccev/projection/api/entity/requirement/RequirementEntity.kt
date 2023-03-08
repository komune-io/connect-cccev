package cccev.projection.api.entity.requirement

import cccev.dsl.model.EvidenceTypeListId
import cccev.projection.api.entity.Relation
import cccev.projection.api.entity.concept.InformationConceptEntity
import cccev.projection.api.entity.evidencetypelist.EvidenceTypeListEntity
import cccev.projection.api.entity.framework.ReferenceFrameworkEntity
import cccev.s2.concept.domain.InformationConceptId
import cccev.s2.requirement.domain.RequirementId
import cccev.s2.requirement.domain.RequirementState
import cccev.s2.requirement.domain.model.RequirementKind
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship
import java.util.UUID
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State

@Node("Requirement")
class RequirementEntity: WithS2Id<RequirementId>, WithS2State<RequirementState> {
    @Id
    var id: RequirementId = UUID.randomUUID().toString()

    var identifier: String? = null

    lateinit var kind: RequirementKind

    var description: String? = null

    var name: String? = null

    var type: String? = null

    @Relationship(type = Relation.IS_DERIVED_FROM)
    lateinit var isDerivedFrom: List<ReferenceFrameworkEntity>

    @Relationship(type = Relation.HAS_REQUIREMENT, direction = Relationship.Direction.INCOMING)
    lateinit var hasRequirement: List<RequirementEntity>

//    @Relationship(type = Relation.IS_REQUIREMENT_OF, direction = Relationship.Direction.INCOMING)
//    lateinit var isRequirementOf: List<RequirementId>

    @Relationship(type = Relation.HAS_QUALIFIED_RELATION, direction = Relationship.Direction.OUTGOING)
    lateinit var hasQualifiedRelation: List<RequirementId>

    @Relationship(type = Relation.HAS_CONCEPT, direction = Relationship.Direction.OUTGOING)
    lateinit var hasConcept: List<InformationConceptEntity>

    @Relationship(type = Relation.HAS_EVIDENCE_TYPE_LIST, direction = Relationship.Direction.OUTGOING)
    lateinit var hasEvidenceTypeList: List<EvidenceTypeListEntity>

    lateinit var status: RequirementState
    override fun s2Id() = id

    override fun s2State() = status
}
