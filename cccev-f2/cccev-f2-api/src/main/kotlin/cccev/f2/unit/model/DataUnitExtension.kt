package cccev.f2.unit.model

import cccev.core.unit.entity.DataUnitEntity
import cccev.core.unit.entity.DataUnitOptionEntity
import cccev.dsl.model.DataUnitIdentifier
import cccev.dsl.model.DataUnitOptionIdentifier
import cccev.dsl.model.nullIfEmpty
import cccev.f2.CccevFlatGraph

fun DataUnitEntity.flattenTo(graph: CccevFlatGraph): DataUnitIdentifier {
    graph.units[identifier] = DataUnitFlat(
        id = id,
        identifier = identifier,
        name = name,
        description = description,
        notation = notation,
        type = type.name,
        optionIdentifiers = options.map { it.flattenTo(graph) }.nullIfEmpty()
    )
    return identifier
}

fun DataUnitOptionEntity.flattenTo(graph: CccevFlatGraph): DataUnitOptionIdentifier {
    graph.unitOptions[identifier] = cccev.dsl.model.DataUnitOption(
        identifier = identifier,
        name = name,
        value = value,
        order = order,
        icon = icon,
        color = color,
    )
    return identifier
}
