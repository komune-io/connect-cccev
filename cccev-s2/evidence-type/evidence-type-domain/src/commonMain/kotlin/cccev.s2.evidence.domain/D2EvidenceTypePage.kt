package cccev.s2.evidence.domain

/**
 * An EvidenceType represents the type of evidence used to support a requirement or information.
 * It provides a categorization mechanism to differentiate between the different forms of evidence that can be used to support a requirement,
 * such as test results, documentation, certification, or expert judgment. <br/>
 * See https://semiceu.github.io/CCCEV/releases/2.00/#Evidence%20Type
 * @d2 page
 * @title Core/Evidence Type
 */
interface D2EvidenceTypePage

/**
 * @d2 service
 * @parent [D2EvidenceTypePage]
 */
interface D2EvidenceTypeService: EvidenceTypeAggregate, EvidenceTypeFinder
