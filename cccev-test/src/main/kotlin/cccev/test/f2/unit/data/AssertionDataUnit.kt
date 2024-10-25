package cccev.test.f2.unit.data

import cccev.core.unit.entity.DataUnit
import cccev.core.unit.entity.DataUnitRepository
import cccev.core.unit.model.DataUnitId
import cccev.core.unit.model.DataUnitType
import org.assertj.core.api.Assertions
import s2.bdd.assertion.AssertionBdd
import s2.bdd.repository.AssertionApiEntity

fun AssertionBdd.dataUnit(unitRepository: DataUnitRepository) = AssertionDataUnit(unitRepository)

class AssertionDataUnit(
    private val repository: DataUnitRepository
): AssertionApiEntity<DataUnit, DataUnitId, AssertionDataUnit.DataUnitAssert>() {

    override suspend fun findById(id: DataUnitId) = repository.findById(id)
    override suspend fun assertThat(entity: DataUnit) = DataUnitAssert(entity)

    inner class DataUnitAssert(
        private val unit: DataUnit
    ) {
        fun hasFields(
            id: DataUnitId = unit.id,
            name: String = unit.name,
            description: String = unit.description,
            notation: String? = unit.notation,
            type: DataUnitType = unit.type
        ) = also {
            Assertions.assertThat(unit.id).isEqualTo(id)
            Assertions.assertThat(unit.name).isEqualTo(name)
            Assertions.assertThat(unit.description).isEqualTo(description)
            Assertions.assertThat(unit.notation).isEqualTo(notation)
            Assertions.assertThat(unit.type).isEqualTo(type)
        }
    }
}
