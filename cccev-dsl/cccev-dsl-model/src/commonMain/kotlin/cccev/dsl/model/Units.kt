package cccev.dsl.model

import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

/**
 * @d2 hidden
 * @example "aeb2c258-9eab-4e55-9e52-77db89aaabfb"
 */
typealias DataUnitId = String

/**
 * @d2 hidden
 * @example "TheDataUnitIdentifier"
 */
typealias DataUnitIdentifier = String

/**
 * @order 1
 * @d2 model
 * @title DSL/DataUnit
 * @parent [cccev.dsl.model.d2.D2DataUnitPage]
 */
@JsExport
@JsName("DataUnitDTO")
interface DataUnitDTO {
    val identifier: DataUnitIdentifier
    val name: String
    val description: String?
    val notation: String?
    val type: DataUnitType
    val options: List<DataUnitOption>?
}

/**
 * @d2 inherit
 */
@Serializable
open class DataUnit(
    override val identifier: DataUnitIdentifier,
    override val name: String,
    override val description: String? = null,
    override val notation: String? = null,
    override val type: DataUnitType,
    override val options: List<DataUnitOption>? = null
) : DataUnitDTO {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as DataUnit

        if (identifier != other.identifier) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (notation != other.notation) return false
        if (type != other.type) return false
        if (options != other.options) return false

        return true
    }

    override fun hashCode(): Int {
        var result = identifier.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (notation?.hashCode() ?: 0)
        result = 31 * result + type.hashCode()
        result = 31 * result + (options?.hashCode() ?: 0)
        return result
    }
}


object XSDDate : DataUnit(
    identifier = "xsdDate",
    name = "XSDDate",
    description = "Date",
    type = DataUnitType.DATE
)

object XSDDouble : DataUnit(
    identifier = "xsdDouble",
    name = "XSDDouble",
    description = "Nombre réel quelconque",
    type = DataUnitType.NUMBER
)

object XSDInt : DataUnit(
    identifier = "xsdInt",
    name = "XSDInt",
    description = "Nombre entier quelconque",
    type = DataUnitType.NUMBER
)

object XSDString : DataUnit(
    identifier = "xsdString",
    name = "XSDString",
    description = "Chaîne de caractères quelconque",
    type = DataUnitType.STRING
)

object XSDBoolean : DataUnit(
    identifier = "xsdBoolean",
    name = "XSDBoolean",
    description = "Vrai ou faux",
    type = DataUnitType.BOOLEAN
)

object Duration {
    object Hour : DataUnit(
        identifier = "hourDuration",
        name = "Durée en heures",
        description = "Durée en heures",
        notation = "h",
        type = DataUnitType.NUMBER
    )

    object Year : DataUnit(
        identifier = "yearDuration",
        name = "Durée en années",
        description = "Durée en années",
        notation = "an(s)",
        type = DataUnitType.NUMBER
    )
}

object Ratio : DataUnit(
    identifier = "ration",
    name = "Ration",
    description = "Ratio entre deux valeurs",
    notation = "%",
    type = DataUnitType.NUMBER
)

object SquareMeter : DataUnit(
    identifier = "squareMeter",
    name = "Mètre carré",
    description = "Mètre carré",
    notation = "m²",
    type = DataUnitType.NUMBER
)

object CubicMeter : DataUnit(
    identifier = "cubicMeter",
    name = "Mètre cube",
    description = "Mètre cube",
    notation = "m³",
    type = DataUnitType.NUMBER
)
