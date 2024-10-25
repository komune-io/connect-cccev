package cccev.core.unit.model

import cccev.core.unit.D2DataUnitPage

/**
 * Type of a data. <br/>
 * Can be either of: BOOLEAN, DATE, NUMBER, STRING
 * @d2 model
 * @parent [D2DataUnitPage]
 * @order 20
 */
enum class DataUnitType {
    BOOLEAN, DATE, NUMBER, STRING
}
