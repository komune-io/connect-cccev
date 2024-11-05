package cccev.core.certification.entity

import cccev.core.concept.entity.InformationConceptEntity
import cccev.dsl.model.SupportedValueId
import cccev.dsl.model.SupportedValueIdentifier
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import org.neo4j.ogm.annotation.Version

@NodeEntity(SupportedValueEntity.LABEL)
class SupportedValueEntity {
    companion object {
        const val LABEL = "SupportedValue"
        const val IS_SUPPORTED_BY = "IS_SUPPORTED_BY"
        const val PROVIDES_VALUE_FOR = "PROVIDES_VALUE_FOR"
    }
    @Id
    lateinit var id: SupportedValueId

    lateinit var identifier: SupportedValueIdentifier

    var value: String? = null

    @Relationship(PROVIDES_VALUE_FOR)
    lateinit var concept: InformationConceptEntity

    @Relationship(IS_SUPPORTED_BY)
    var evidences: MutableList<EvidenceEntity> = mutableListOf()

    @Version
    var version: Long? = null

    var creationDate: Long = System.currentTimeMillis()
}
