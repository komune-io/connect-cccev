package cccev.core.requirement

/**
 * The Requirement model represents a generic class for any type of prerequisite that may be desired, needed, or imposed as an obligation. <br/>
 * It has three subtypes: Criterion, InformationRequirement, and Constraint. <br/>
 *
 * Criterion is a Requirement that defines a list of Requirements.
 * This type of Requirement is used to create a set of criteria that must be met in order for a given system to satisfy its objectives. <br/>
 *
 * InformationRequirement represents the need to obtain a necessary piece of information, without any condition that depends on the actual content of this information.
 * This type of Requirement is used to specify the information that must be available to a system but needs human intervention in order to be validated (e.g. a pdf document),
 * or that is used as-is to deduce other pieces of information. <br/>
 *
 * Constraint defines a clear and concrete constraint over a specific piece of information.
 * This type of Requirement is used to specify conditions that must be met in order for a given system to satisfy its objectives,
 * such as restrictions on the values of certain parameters or fields. <br/>
 *
 * See https://semiceu.github.io/CCCEV/releases/2.00/#Requirement
 * @d2 page
 * @title Requirement
 */
interface D2RequirementPage
