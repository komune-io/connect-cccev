package cccev.projection.api.entity.requirement

import cccev.projection.api.entity.NodeLabel
import cccev.projection.api.entity.Relation
import cccev.projection.api.entity.SnapRepositoryBase
import cccev.s2.requirement.domain.RequirementId
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Repository
interface RequirementRepository: ReactiveNeo4jRepository<RequirementEntity, RequirementId> {
    // TODO also fetch other relations (info concepts, evidence type lists, ...) or it won't be filled in the entity
    @Query("" +
            "MATCH (root:${NodeLabel.REQUIREMENT}) WHERE root.id IN \$ids\n" +
            "OPTIONAL MATCH (root)-[hr:${Relation.HAS_REQUIREMENT}*1..]->(child:${NodeLabel.REQUIREMENT} {type: \$type})" +
            "RETURN root, collect(hr), collect(child)"
    )
    fun findByIdWithChildrenOfType(ids: Collection<RequirementId>, type: String): Flux<RequirementEntity>

    // TODO also fetch other relations (info concepts, evidence type lists, ...) or it won't be filled in the entity
    @Query("" +
            "MATCH (root:${NodeLabel.REQUIREMENT} WHERE root.id IN \$ids)" +
            "-[hr:${Relation.HAS_REQUIREMENT}*1..]->" +
            "(child:${NodeLabel.REQUIREMENT} {type: \$type})\n" +
            "RETURN child, collect(hr)"
    )
    fun findAllChildrenOfType(ids: Collection<RequirementId>, type: String): Flux<RequirementEntity>
}

@Service
class RequirementSnapRepository(
    override val repository: RequirementRepository
): SnapRepositoryBase<RequirementEntity, RequirementId>()
