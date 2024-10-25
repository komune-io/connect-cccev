package cccev.dsl.model

import io.komune.fs.s2.file.domain.model.FilePath
import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("DataUnitDTO")
interface DataUnitDTO {
    val identifier: String
    val name: String
    val description: String
    val notation: String?
    val type: DataUnitType
    val options: List<DataUnitOption>?
}

@Serializable
open class DataUnit(
    override val identifier: String,
    override val name: String,
    override val description: String,
    override val notation: String? = null,
    override val type: DataUnitType,
    override val options: List<DataUnitOption>? = null
): DataUnitDTO

@Serializable
open class DataUnitOption(
    val identifier: String,
    val name: String,
    val value: String,
    val order: Int,
    val icon: FilePath?,
    val color: String?
)

@Suppress("EnumNaming")
enum class DataUnitType {
    boolean, date, number, string
}

object XSDDate: DataUnit(
    identifier = "xsdDate",
    name = "XSDDate",
    description = "Date",
    type = DataUnitType.date
)

object XSDDouble: DataUnit(
    identifier = "xsdDouble",
    name = "XSDDouble",
    description = "Nombre réel quelconque",
    type = DataUnitType.number
)

object XSDInt: DataUnit(
    identifier = "xsdInt",
    name = "XSDInt",
    description = "Nombre entier quelconque",
    type = DataUnitType.number
)

object XSDString: DataUnit(
    identifier = "xsdString",
    name = "XSDString",
    description = "Chaîne de caractères quelconque",
    type = DataUnitType.string
)

object XSDBoolean: DataUnit(
    identifier = "xsdBoolean",
    name = "XSDBoolean",
    description = "Vrai ou faux",
    type = DataUnitType.boolean
)

object Duration {
    object Hour: DataUnit(
        identifier = "hourDuration",
        name = "Durée en heures",
        description = "Durée en heures",
        notation = "h",
        type = DataUnitType.number
    )
    object Year: DataUnit(
        identifier = "yearDuration",
        name = "Durée en années",
        description = "Durée en années",
        notation = "an(s)",
        type = DataUnitType.number
    )
}

object Ratio: DataUnit(
    identifier = "ration",
    name = "Ration",
    description = "Ratio entre deux valeurs",
    notation = "%",
    type = DataUnitType.number
)

object SquareMeter: DataUnit(
    identifier = "squareMeter",
    name = "Mètre carré",
    description = "Mètre carré",
    notation = "m²",
    type = DataUnitType.number
)

object CubicMeter: DataUnit(
    identifier = "cubicMeter",
    name = "Mètre cube",
    description = "Mètre cube",
    notation = "m³",
    type = DataUnitType.number
)
