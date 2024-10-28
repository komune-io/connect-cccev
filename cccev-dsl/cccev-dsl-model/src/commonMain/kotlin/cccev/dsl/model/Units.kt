package cccev.dsl.model

import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * @d2 hidden
 * @visual json "aeb2c258-9eab-4e55-9e52-77db89aaabfb"
 */
typealias DataUnitId = String

/**
 * @d2 hidden
 * @visual json "TheDataUnitIdentifier"
 */
typealias DataUnitIdentifier = String

@JsExport
@JsName("DataUnitDTO")
interface DataUnitDTO {
    val identifier: DataUnitIdentifier
    val name: String
    val description: String
    val notation: String?
    val type: DataUnitType
    val options: List<DataUnitOption>?
}

@Serializable
open class DataUnit(
    override val identifier: DataUnitIdentifier,
    override val name: String,
    override val description: String,
    override val notation: String? = null,
    override val type: DataUnitType,
    override val options: List<DataUnitOption>? = null
): DataUnitDTO


object XSDDate: DataUnit(
    identifier = "xsdDate",
    name = "XSDDate",
    description = "Date",
    type = DataUnitType.DATE
)

object XSDDouble: DataUnit(
    identifier = "xsdDouble",
    name = "XSDDouble",
    description = "Nombre réel quelconque",
    type = DataUnitType.NUMBER
)

object XSDInt: DataUnit(
    identifier = "xsdInt",
    name = "XSDInt",
    description = "Nombre entier quelconque",
    type = DataUnitType.NUMBER
)

object XSDString: DataUnit(
    identifier = "xsdString",
    name = "XSDString",
    description = "Chaîne de caractères quelconque",
    type = DataUnitType.STRING
)

object XSDBoolean: DataUnit(
    identifier = "xsdBoolean",
    name = "XSDBoolean",
    description = "Vrai ou faux",
    type = DataUnitType.BOOLEAN
)

object Duration {
    object Hour: DataUnit(
        identifier = "hourDuration",
        name = "Durée en heures",
        description = "Durée en heures",
        notation = "h",
        type = DataUnitType.NUMBER
    )
    object Year: DataUnit(
        identifier = "yearDuration",
        name = "Durée en années",
        description = "Durée en années",
        notation = "an(s)",
        type = DataUnitType.NUMBER
    )
}

object Ratio: DataUnit(
    identifier = "ration",
    name = "Ration",
    description = "Ratio entre deux valeurs",
    notation = "%",
    type = DataUnitType.NUMBER
)

object SquareMeter: DataUnit(
    identifier = "squareMeter",
    name = "Mètre carré",
    description = "Mètre carré",
    notation = "m²",
    type = DataUnitType.NUMBER
)

object CubicMeter: DataUnit(
    identifier = "cubicMeter",
    name = "Mètre cube",
    description = "Mètre cube",
    notation = "m³",
    type = DataUnitType.NUMBER
)
