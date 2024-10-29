package cccev.core.certification.entity

import cccev.core.concept.entity.InformationConcept
import cccev.dsl.model.SupportedValueId
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import org.neo4j.ogm.annotation.Version

@NodeEntity(SupportedValue.LABEL)
class SupportedValue {
    companion object {
        const val LABEL = "SupportedValue"
        const val IS_SUPPORTED_BY = "IS_SUPPORTED_BY"
        const val PROVIDES_VALUE_FOR = "PROVIDES_VALUE_FOR"
    }
    @Id
    lateinit var id: SupportedValueId

    var value: String? = null

    @Relationship(PROVIDES_VALUE_FOR)
    lateinit var concept: InformationConcept

    @Relationship(IS_SUPPORTED_BY)
    var evidences: MutableList<Evidence> = mutableListOf()

    @Version
    var version: Long? = null

    var creationDate: Long = System.currentTimeMillis()
}
