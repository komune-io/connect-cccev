package cccev.core.certification.entity

import cccev.core.concept.entity.InformationConceptEntity
import cccev.core.requirement.entity.RequirementEntity
import cccev.core.unit.entity.DataUnitEntity
import cccev.dsl.model.InformationConceptIdentifier
import cccev.core.evidencetype.entity.EvidenceTypeEntity
import cccev.dsl.model.CertificationId
import cccev.dsl.model.EvidenceId
import cccev.dsl.model.EvidenceTypeId
import cccev.dsl.model.RequirementCertificationId
import cccev.dsl.model.SupportedValueId
import cccev.infra.neo4j.findById
import cccev.infra.neo4j.returnWholeEntity
import cccev.infra.neo4j.session
import org.neo4j.ogm.session.SessionFactory
import org.springframework.stereotype.Service

@Service
class CertificationRepository(
    private val sessionFactory: SessionFactory
) {
    suspend fun findById(id: CertificationId): CertificationEntity? = sessionFactory.session { session ->
        session.findById<CertificationEntity>(id, CertificationEntity.LABEL)
    }

    suspend fun findRequirementCertificationById(
        id: RequirementCertificationId
    ): RequirementCertificationEntity? = sessionFactory.session { session ->
        session.findById<RequirementCertificationEntity>(id, RequirementCertificationEntity.LABEL)
    }

    suspend fun findSupportedValueById(id: SupportedValueId): SupportedValueEntity? = sessionFactory.session { session ->
        session.findById<SupportedValueEntity>(id, SupportedValueEntity.LABEL)
    }

    suspend fun findEvidenceById(id: EvidenceId): EvidenceEntity? = sessionFactory.session { session ->
        session.findById<EvidenceEntity>(id, EvidenceEntity.LABEL)
    }

    suspend fun hasRequirementCertification(
        certificationId: String, requirementCertificationId: String
    ): Boolean = sessionFactory.session { session ->
        session.queryForObject(
            java.lang.Boolean::class.java,
            "RETURN EXISTS(" +
                    "(c:${CertificationEntity.LABEL} {id: \$cId})" +
                    "-[:${RequirementCertificationEntity.IS_CERTIFIED_BY}*1..]->(rc:${RequirementCertificationEntity.LABEL} {id: \$rcId})" +
                    ")",
            mapOf("cId" to certificationId, "rcId" to requirementCertificationId)
        ).booleanValue()
    }

    suspend fun findRequirementCertificationsLinkedToInformationConcept(
        certificationId: CertificationId,
        rootRequirementCertificationId: RequirementCertificationId?,
        informationConceptIdentifier: InformationConceptIdentifier
    ): List<RequirementCertificationEntity> = sessionFactory.session { session ->
        session.query(
            "MATCH (:${CertificationEntity.LABEL} {id: \$cId})" +
                    (if (rootRequirementCertificationId != null) {
                        "-[:${RequirementCertificationEntity.IS_CERTIFIED_BY}*1..]->(:${RequirementCertificationEntity.LABEL} {id: \$rcId})"
                    } else { "" }) +
                    "-[:${RequirementCertificationEntity.IS_CERTIFIED_BY}*0..]->(rc:${RequirementCertificationEntity.LABEL})" +
                    "-[:${RequirementCertificationEntity.CERTIFIES}]->(:${RequirementEntity.LABEL})" +
                    "-->(:${InformationConceptEntity.LABEL} {identifier: \$icId})"
                        .returnWholeEntity("rc"),
            mapOf("cId" to certificationId, "rcId" to rootRequirementCertificationId, "icId" to informationConceptIdentifier),
        ).map { it["rc"] as RequirementCertificationEntity }
    }

    suspend fun findRequirementCertificationsLinkedToEvidenceType(
        certificationId: CertificationId,
        rootRequirementCertificationId: RequirementCertificationId?,
        evidenceType: EvidenceTypeId
    ): List<RequirementCertificationEntity> = sessionFactory.session { session ->
        session.query(
            "MATCH (:${CertificationEntity.LABEL} {id: \$cId})" +
                    (if (rootRequirementCertificationId != null) {
                        "-[:${RequirementCertificationEntity.IS_CERTIFIED_BY}*1..]->(:${RequirementCertificationEntity.LABEL} {id: \$rcId})"
                    } else { "" }) +
                    "-[:${RequirementCertificationEntity.IS_CERTIFIED_BY}*0..]->(rc:${RequirementCertificationEntity.LABEL})" +
                    "-[:${RequirementCertificationEntity.CERTIFIES}]->(:${RequirementEntity.LABEL})" +
                    "-->(:${EvidenceTypeEntity.LABEL} {id: \$etId})"
                        .returnWholeEntity("rc"),
            mapOf("cId" to certificationId, "rcId" to rootRequirementCertificationId, "etId" to evidenceType),
        ).map { it["rc"] as RequirementCertificationEntity }
    }

    suspend fun findParentsOf(
        requirementCertificationId: RequirementCertificationId
    ): List<RequirementCertificationEntity> = sessionFactory.session { session ->
        session.query(
            "MATCH (:${RequirementCertificationEntity.LABEL} {id: \$rcId})" +
                    "<-[:${RequirementCertificationEntity.IS_CERTIFIED_BY}]-(parent:${RequirementCertificationEntity.LABEL})"
                        .returnWholeEntity("parent"),
            mapOf("rcId" to requirementCertificationId)
        ).map { it["parent"] as RequirementCertificationEntity }
    }

    suspend fun findAllSupportedValues(
        certificationId: CertificationId,
        rootRequirementCertificationId: RequirementCertificationId?
    ): List<SupportedValueEntity> = sessionFactory.session { session ->
        session.query(
            "MATCH (c:${CertificationEntity.LABEL} {id: \$cId})" +
                    (if (rootRequirementCertificationId != null) {
                        "-[:${RequirementCertificationEntity.IS_CERTIFIED_BY}*1..]->(:${RequirementCertificationEntity.LABEL} {id: \$rcId})"
                    } else { "" }) +
                    "-[:${RequirementCertificationEntity.IS_CERTIFIED_BY}*0..]->(rc:${RequirementCertificationEntity.LABEL})" +
                    "-[uses_value:${RequirementCertificationEntity.USES_VALUE}]->(sv:${SupportedValueEntity.LABEL})" +
                    "-[provides_value_for:${SupportedValueEntity.PROVIDES_VALUE_FOR}]->(ic:${InformationConceptEntity.LABEL})" +
                    "-[has_unit:${InformationConceptEntity.HAS_UNIT}]->(du:${DataUnitEntity.LABEL})" +
                    "\nRETURN distinct sv, collect(provides_value_for), collect(distinct, ic), collect(has_unit), collect(distinct du)",
            mapOf("cId" to certificationId, "rcId" to rootRequirementCertificationId)
        ).map { it["sv"] as SupportedValueEntity }
    }

    suspend fun findSupportedValuesSupportedByEvidenceType(
        evidenceTypeId: EvidenceTypeId,
        requirementCertificationIds: Collection<RequirementCertificationId>
    ): List<SupportedValueEntity> = sessionFactory.session { session ->
        session.query(
            "MATCH (:${EvidenceTypeEntity.LABEL} {id: \$etId})" +
                    "-[:${EvidenceTypeEntity.SUPPORTS_CONCEPT}]->(ic:${InformationConceptEntity.LABEL})" +
                    "\nMATCH (rc:${RequirementCertificationEntity.LABEL})-[*1..]->(sv:${SupportedValueEntity.LABEL})" +
                    "-[:${SupportedValueEntity.PROVIDES_VALUE_FOR}]->(ic)" +
                    "\nWHERE rc.id IN \$rcIds"
                        .returnWholeEntity("sv"),
            mapOf("rcIds" to requirementCertificationIds, "etId" to evidenceTypeId)
        ).map { it["sv"] as SupportedValueEntity }
    }

    /**
     * Find the closest parent certification that has a supporting evidence for the given supported value, and return this evidence.
     */
    suspend fun findSupportingEvidenceFor(supportedValueId: SupportedValueId): EvidenceEntity? = sessionFactory.session { session ->
        session.query(
            "MATCH (sv:${SupportedValueEntity.LABEL} {id: \$svId})" +
                    "-[:${SupportedValueEntity.PROVIDES_VALUE_FOR}]->(ic:${InformationConceptEntity.LABEL})" +
                    "\nMATCH p=(sv)" +
                    "<-[:${RequirementCertificationEntity.USES_VALUE}]-(:${RequirementCertificationEntity.LABEL})" +
                    "<-[:${RequirementCertificationEntity.IS_CERTIFIED_BY}*0..]-(:${RequirementCertificationEntity.LABEL})" +
                    "-[:${RequirementCertificationEntity.HAS_EVIDENCE}]->(e:${EvidenceEntity.LABEL})" +
                    "-[:${EvidenceEntity.IS_CONFORMANT_TO}]->(:${EvidenceTypeEntity.LABEL})" +
                    "-[:${EvidenceTypeEntity.SUPPORTS_CONCEPT}]->(ic)" +
                    "\nWITH e" +
                    "\nORDER BY length(p)" +
                    "\nLIMIT 1"
                        .returnWholeEntity("e"),
            mapOf("svId" to supportedValueId)
        ).map { it["e"] as EvidenceEntity }
            .firstOrNull()
    }

    suspend fun save(entity: CertificationEntity, depth: Int = -1) = sessionFactory.session { session ->
        session.save(entity, depth)
    }

    suspend fun save(entity: RequirementCertificationEntity, depth: Int = -1) = sessionFactory.session { session ->
        session.save(entity, depth)
    }
}
