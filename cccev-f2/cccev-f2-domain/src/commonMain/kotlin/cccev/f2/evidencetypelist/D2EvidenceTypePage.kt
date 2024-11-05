package cccev.f2.evidencetypelist

/**
 * Group of Evidence Types for conforming to a Requirement.
 * An Evidence Type List is satisfied, if and only if, for all included Evidence Types in this List,
 * corresponding conformant Evidence(s) are supporting the Requirement having this List.
 * The Evidence Type List describes thus an AND condition on the different Evidence Types within the list
 * and an OR condition between two or more Evidence Type Lists. Combinations of alternative Lists can be provided
 * for a respondent of a Requirement to choose amongst them.
 *
 * See https://semiceu.github.io/CCCEV/releases/2.1.0/#EvidenceTypeList
 * @d2 page
 * @title Evidence Type
 */
interface D2EvidenceTypeListPage