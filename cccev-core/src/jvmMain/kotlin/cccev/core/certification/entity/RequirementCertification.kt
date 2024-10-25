package cccev.core.certification.entity

import cccev.core.certification.model.RequirementCertificationId
import cccev.core.requirement.entity.Requirement
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import org.neo4j.ogm.annotation.Version

@NodeEntity(RequirementCertification.LABEL)
class RequirementCertification {
    companion object {
        const val LABEL = "RequirementCertification"

        const val CERTIFIES = "CERTIFIES"
        const val IS_CERTIFIED_BY = "IS_CERTIFIED_BY"
        const val USES_VALUE = "USES_VALUE"
    }

    @Id
    lateinit var id: RequirementCertificationId

    @Relationship(CERTIFIES)
    lateinit var requirement: Requirement

    @Relationship(IS_CERTIFIED_BY)
    var subCertifications: MutableList<RequirementCertification> = mutableListOf()

    @Relationship(USES_VALUE)
    var values: MutableList<SupportedValue> = mutableListOf()

    /**
     * Result of the requirement's enablingCondition, or true if the requirement has no enablingCondition.
     */
    var isEnabled: Boolean = false

    /**
     * Result of the requirement's validatingCondition, or true if the requirement has no validatingCondition.
     */
    var isValidated: Boolean = false

    /**
     * True if the requirement has values for every information concepts.
     */
    var hasAllValues: Boolean = false

    /**
     * True if the requirement is enabled, validated, has values for all its information concepts,
     * and all its sub-requirements are fulfilled or disabled.
     */
    var isFulfilled: Boolean = false

    @Version
    var version: Long? = null

    var creationDate: Long = System.currentTimeMillis()
}
