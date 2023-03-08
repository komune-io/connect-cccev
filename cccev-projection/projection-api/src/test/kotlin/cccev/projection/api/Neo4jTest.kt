package cccev.projection.api

import cccev.projection.api.entity.requirement.RequirementEntity
import cccev.projection.api.entity.requirement.RequirementRepository
import cccev.s2.requirement.domain.model.RequirementKind
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(classes = [Neo4jTestApplication::class])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension::class)
class Neo4jTest {

    @Autowired
    private lateinit var requirementRepository: RequirementRepository

    @Test
    fun test() = runTest {
        val requirementPoP = RequirementEntity().apply {
            kind = RequirementKind.INFORMATION
            identifier = "PoP"
            name = "Protocol"
        }
        val requirementPoP2 = RequirementEntity().apply {
            kind = RequirementKind.INFORMATION
            identifier = "PoP2"
            name = "Protocol"
        }
        val requirement = RequirementEntity().apply {
            kind = RequirementKind.INFORMATION
            identifier = "P"
            name = "Protocol"
//            hasRequirement = listOf("PoP", "PoP2")
            hasRequirement = listOf(
                RequirementEntity().apply {
                    kind = RequirementKind.INFORMATION
                    identifier = "PoP"
                    name = "ProtocolOfProtocol"
                    hasRequirement = listOf(
                        RequirementEntity().apply {
                            kind = RequirementKind.INFORMATION
                            identifier = "PoPinP"
                            name = "ProtocolOfProtocolInProtocol"
                        }
                    )
                }
            )
        }
        requirementRepository.save(requirement).awaitSingle()
    }
}
