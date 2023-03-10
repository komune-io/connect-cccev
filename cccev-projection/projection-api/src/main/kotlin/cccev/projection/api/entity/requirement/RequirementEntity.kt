package cccev.projection.api.entity.requirement

import cccev.dsl.model.EvidenceTypeListId
import cccev.projection.api.entity.EntityBase
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
class RequirementEntity: EntityBase<RequirementId, RequirementState>() {
    var identifier: String? = null

    lateinit var kind: RequirementKind

    var name: String? = null

    var description: String? = null

    var type: String? = null

    @Relationship(type = Relation.IS_DERIVED_FROM)
    lateinit var isDerivedFrom: List<ReferenceFrameworkEntity>

    @Relationship(type = Relation.HAS_REQUIREMENT)
    lateinit var hasRequirement: MutableList<RequirementEntity>

//    @Relationship(type = Relation.IS_REQUIREMENT_OF, direction = Relationship.Direction.INCOMING)
//    lateinit var isRequirementOf: List<RequirementEntity>

    @Relationship(type = Relation.HAS_QUALIFIED_RELATION)
    lateinit var hasQualifiedRelation: MutableList<RequirementEntity>

    @Relationship(type = Relation.HAS_CONCEPT)
    lateinit var hasConcept: MutableList<InformationConceptEntity>

    @Relationship(type = Relation.HAS_EVIDENCE_TYPE_LIST)
    lateinit var hasEvidenceTypeList: MutableList<EvidenceTypeListEntity>
}
