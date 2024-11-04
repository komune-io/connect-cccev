package cccev.core.concept.entity

import cccev.core.unit.entity.DataUnitEntity
import cccev.dsl.model.InformationConceptId
import cccev.dsl.model.InformationConceptIdentifier
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import org.neo4j.ogm.annotation.Version

@NodeEntity(InformationConceptEntity.LABEL)
class InformationConceptEntity {
    companion object {
        const val LABEL = "InformationConcept"
        const val DEPENDS_ON = "DEPENDS_ON"
        const val HAS_UNIT = "HAS_UNIT"
    }

    @Id
    lateinit var id: InformationConceptId

    lateinit var identifier: InformationConceptIdentifier

    lateinit var name: String

    var description: String? = null

    @Relationship(HAS_UNIT)
    lateinit var unit: DataUnitEntity

    var expressionOfExpectedValue: String? = null

    @Relationship(DEPENDS_ON)
    var dependencies: MutableList<InformationConceptEntity> = mutableListOf()

    @Version
    var version: Long? = null

    var creationDate: Long = System.currentTimeMillis()
}
