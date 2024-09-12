package cccev.test.s2.unit.data

import cccev.s2.unit.domain.model.DataUnitType
import org.springframework.context.annotation.Configuration
import s2.bdd.data.parser.EntryParser

@Configuration
class DataUnitTypeParser: EntryParser<DataUnitType>(
    output = DataUnitType::class,
    parseErrorMessage = "Expected ${DataUnitType::class.simpleName} value",
    parser = DataUnitType::valueOf
)
