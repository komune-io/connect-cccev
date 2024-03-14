package cccev.f2.unit.api.model

import cccev.core.unit.entity.DataUnit
import cccev.core.unit.entity.DataUnitOption
import cccev.core.unit.model.DataUnitIdentifier
import cccev.core.unit.model.DataUnitOptionIdentifier
import cccev.f2.commons.CccevFlatGraph
import cccev.f2.unit.domain.model.DataUnitFlat

fun DataUnitOption.toDTO() = cccev.f2.unit.domain.model.DataUnitOption(
    id = id,
    identifier = identifier,
    name = name,
    value = value,
    order = order,
    icon = icon,
    color = color,
)

fun DataUnit.flattenTo(graph: CccevFlatGraph): DataUnitIdentifier {
    graph.units[identifier] = DataUnitFlat(
        id = id,
        identifier = identifier,
        name = name,
        description = description,
        notation = notation,
        type = type.name,
        optionIdentifiers = options.map { it.flattenTo(graph) }
    )
    return identifier
}

fun DataUnitOption.flattenTo(graph: CccevFlatGraph): DataUnitOptionIdentifier {
    graph.unitOptions[identifier] = this.toDTO()
    return identifier
}
