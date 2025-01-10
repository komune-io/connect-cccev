package cccev.test.f2.requirement.data

import cccev.dsl.model.RequirementKind
import org.springframework.context.annotation.Configuration
import s2.bdd.data.parser.EntryParser

@Configuration
class RequirementKindParser: EntryParser<RequirementKind>(
    output = RequirementKind::class,
    parseErrorMessage = "Expected ${RequirementKind::class.simpleName} value",
    parser = RequirementKind::valueOf
)
