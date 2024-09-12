package cccev.test.s2.requirement.data

import cccev.s2.requirement.domain.model.RequirementKind
import kotlin.reflect.jvm.jvmName
import org.springframework.context.annotation.Configuration
import s2.bdd.data.parser.EntryParser

@Configuration
class RequirementKindParser: EntryParser<RequirementKind>(
    output = RequirementKind::class,
    parseErrorMessage = "Expected ${RequirementKind::class.simpleName} value",
    parser = RequirementKind::valueOf
)
