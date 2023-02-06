package cccev.s2.unit.domain

/**
 * A DataUnit represents the type or unit of a specific value.
 * It can be used to describe a variety of different types of data, such as physical measurements (e.g., meters, square meters, hours),
 * dates, numbers, or units of power and weight (e.g., watts, kilograms).
 * @d2 page
 * @title Core/DataUnit
 */
interface D2DataUnitPage

/**
 * @d2 service
 * @parent [D2DataUnitPage]
 */
interface D2DataUnitService: DataUnitAggregate, DataUnitFinder
