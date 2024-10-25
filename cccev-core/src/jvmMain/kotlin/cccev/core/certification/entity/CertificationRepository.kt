package cccev.core.certification.entity

import cccev.core.certification.model.CertificationId
import cccev.core.certification.model.RequirementCertificationId
import cccev.core.concept.entity.InformationConcept
import cccev.core.concept.model.InformationConceptIdentifier
import cccev.core.requirement.entity.Requirement
import cccev.core.unit.entity.DataUnit
import cccev.infra.neo4j.session
import org.neo4j.ogm.session.SessionFactory
import org.springframework.stereotype.Service

@Service
class CertificationRepository(
    private val sessionFactory: SessionFactory
) {
    suspend fun findById(id: CertificationId): Certification? = sessionFactory.session { session ->
        session.queryForObject(
            Certification::class.java,
            "MATCH (c:${Certification.LABEL} {id: \$id})" +
                    "\nCALL apoc.path.subgraphAll(c, {})" +
                    "\nYIELD nodes, relationships" +
                    "\nRETURN nodes, relationships",
            mapOf("id" to id)
        )
    }
    suspend fun findRequirementCertificationById(
        id: RequirementCertificationId
    ): RequirementCertification? = sessionFactory.session { session ->
        session.query(
            "MATCH (rc:${RequirementCertification.LABEL} {id: \$id})" +
                    "\nCALL apoc.path.subgraphAll(rc, {})" +
                    "\nYIELD nodes, relationships" +
                    "\nRETURN rc, nodes, relationships",
            mapOf("id" to id)
        ).map { it["rc"] as RequirementCertification }
            .firstOrNull()
    }

    suspend fun hasRequirementCertification(
        certificationId: String, requirementCertificationId: String
    ): Boolean = sessionFactory.session { session ->
        session.queryForObject(
            java.lang.Boolean::class.java,
            "RETURN EXISTS(" +
                    "(c:${Certification.LABEL} {id: \$cId})" +
                    "-[:${RequirementCertification.IS_CERTIFIED_BY}*1..]->(rc:${RequirementCertification.LABEL} {id: \$rcId})" +
                    ")",
            mapOf("cId" to certificationId, "rcId" to requirementCertificationId)
        ).booleanValue()
    }

    suspend fun findRequirementCertificationsLinkedToInformationConcept(
        certificationId: CertificationId,
        rootRequirementCertificationId: RequirementCertificationId?,
        informationConceptIdentifier: InformationConceptIdentifier
    ): List<RequirementCertification> = sessionFactory.session { session ->
        session.query(
            "MATCH (:${Certification.LABEL} {id: \$cId})" +
                    (if (rootRequirementCertificationId != null) {
                        "-[:${RequirementCertification.IS_CERTIFIED_BY}*1..]->(:${RequirementCertification.LABEL} {id: \$rcId})"
                    } else { "" }) +
                    "-[:${RequirementCertification.IS_CERTIFIED_BY}*0..]->(rc:${RequirementCertification.LABEL})" +
                    "-[:${RequirementCertification.CERTIFIES}]->(:${Requirement.LABEL})" +
                    "-->(:${InformationConcept.LABEL} {identifier: \$icId})" +
                    "\nCALL apoc.path.subgraphAll(rc, {})" +
                    "\nYIELD nodes, relationships" +
                    "\nRETURN distinct rc, nodes, relationships",
            mapOf("cId" to certificationId, "rcId" to rootRequirementCertificationId, "icId" to informationConceptIdentifier),
        ).map { it["rc"] as RequirementCertification }
    }

    suspend fun findParentsOf(
        requirementCertificationId: RequirementCertificationId
    ): List<RequirementCertification> = sessionFactory.session { session ->
        session.query(
            "MATCH (:${RequirementCertification.LABEL} {id: \$rcId})" +
                    "<-[:${RequirementCertification.IS_CERTIFIED_BY}]-(parent:${RequirementCertification.LABEL})" +
                    "\nCALL apoc.path.subgraphAll(parent, {})" +
                    "\nYIELD nodes, relationships" +
                    "\nRETURN parent, nodes, relationships",
            mapOf("rcId" to requirementCertificationId)
        ).map { it["parent"] as RequirementCertification }
    }

    suspend fun findAllSupportedValues(
        certificationId: CertificationId,
        rootRequirementCertificationId: RequirementCertificationId?
    ): List<SupportedValue> = sessionFactory.session { session ->
        session.query(
            "MATCH (c:${Certification.LABEL} {id: \$cId})" +
                    (if (rootRequirementCertificationId != null) {
                        "-[:${RequirementCertification.IS_CERTIFIED_BY}*1..]->(:${RequirementCertification.LABEL} {id: \$rcId})"
                    } else { "" }) +
                    "-[:${RequirementCertification.IS_CERTIFIED_BY}*0..]->(rc:${RequirementCertification.LABEL})" +
                    "-[uses_value:${RequirementCertification.USES_VALUE}]->(sv:${SupportedValue.LABEL})" +
                    "-[provides_value_for:${SupportedValue.PROVIDES_VALUE_FOR}]->(ic:${InformationConcept.LABEL})" +
                    "-[has_unit:${InformationConcept.HAS_UNIT}]->(du:${DataUnit.LABEL})" +
                    "\nRETURN distinct sv, collect(provides_value_for), collect(distinct, ic), collect(has_unit), collect(distinct du)",
            mapOf("cId" to certificationId, "rcId" to rootRequirementCertificationId)
        ).map { it["sv"] as SupportedValue }
    }

    suspend fun save(entity: Certification, depth: Int = -1) = sessionFactory.session { session ->
        session.save(entity, depth)
    }

    suspend fun save(entity: RequirementCertification, depth: Int = -1) = sessionFactory.session { session ->
        session.save(entity, depth)
    }
}
