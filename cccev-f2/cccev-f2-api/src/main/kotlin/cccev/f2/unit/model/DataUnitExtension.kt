package cccev.f2.unit.model

import cccev.core.unit.entity.DataUnit
import cccev.core.unit.entity.DataUnitOption
import cccev.dsl.model.DataUnitIdentifier
import cccev.dsl.model.DataUnitOptionIdentifier
import cccev.f2.CccevFlatGraph

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
    graph.unitOptions[identifier] = cccev.dsl.model.DataUnitOption(
//        id = id,
        identifier = identifier,
        name = name,
        value = value,
        order = order,
        icon = icon,
        color = color,
    )
    return identifier
}