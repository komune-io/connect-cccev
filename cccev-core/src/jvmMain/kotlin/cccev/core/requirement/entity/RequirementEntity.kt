package cccev.core.requirement.entity

import cccev.commons.utils.parseJsonTo
import cccev.commons.utils.toJson
import cccev.core.concept.entity.InformationConceptEntity
import cccev.core.evidencetype.entity.EvidenceTypeEntity
import cccev.dsl.model.RequirementId
import cccev.dsl.model.RequirementKind
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import org.neo4j.ogm.annotation.Version

@NodeEntity(RequirementEntity.LABEL)
class RequirementEntity {
    companion object {
        const val LABEL = "Requirement"
        const val HAS_REQUIREMENT = "HAS_REQUIREMENT"
        const val HAS_CONCEPT = "HAS_CONCEPT"
        const val HAS_EVIDENCE_TYPE = "HAS_EVIDENCE_TYPE"
        const val ENABLING_DEPENDS_ON = "ENABLING_DEPENDS_ON"
        const val VALIDATION_DEPENDS_ON = "VALIDATION_DEPENDS_ON"
    }

    @Id
    lateinit var id: RequirementId

    lateinit var identifier: String

    lateinit var kind: RequirementKind

    var name: String? = null

    var description: String? = null

    var type: String? = null

    @Relationship(HAS_REQUIREMENT)
    var subRequirements: MutableList<RequirementEntity> = mutableListOf()

    @Relationship(HAS_CONCEPT)
    var concepts: MutableList<InformationConceptEntity> = mutableListOf()

    @Relationship(HAS_EVIDENCE_TYPE)
    var evidenceTypes: MutableList<EvidenceTypeEntity> = mutableListOf()

    var enablingCondition: String? = null

    @Relationship(ENABLING_DEPENDS_ON)
    var enablingConditionDependencies: MutableList<InformationConceptEntity> = mutableListOf()

    var required: Boolean = true

    var validatingCondition: String? = null

    @Relationship(VALIDATION_DEPENDS_ON)
    var validatingConditionDependencies: MutableList<InformationConceptEntity> = mutableListOf()

    var evidenceValidatingCondition: String? = null

    var order: Int? = null

    private var propertiesJson: String? = null
    var properties: Map<String, String>?
        get() = propertiesJson?.parseJsonTo<Map<String, String>>()
        set(value) { propertiesJson = value?.toJson() }

    @Version
    var version: Long? = null

    var creationDate: Long = System.currentTimeMillis()
}
