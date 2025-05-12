# Core Criterion and Core Evidence Vocabulary Api

The connect-cccev project facilitates managing certification processes. It allows defining complex Requirements based on the standard CCCEV vocabulary, including associated InformationConcepts (specific data points) and needed Evidence (proofs). The system uses a Neo4j graph database to store these interconnected elements and provides an F2 API to create certifications and track their fulfillment by adding evidence and value


## The Core Concepts: Building Blocks of CCCEV

CCCEV gives us three main building blocks:

1.  **Requirement:** This is the rule, the question, or the condition that needs to be met or checked.
  *   *Analogy:* A question in a form ("Are you over 18?") or a task in a checklist ("Provide proof of address").
  *   *Example (Youth Pass):* "Applicant must be under 26 years old."
  *   *CCCEV Definition:* "[A condition or prerequisite that is to be proven by Evidence.](https://semiceu.github.io/CCCEV/releases/2.00/#Requirement)" Requirements can be simple checks (`InformationRequirement`), evaluation criteria (`Criterion`), or specific limitations (`Constraint`). For now, just think of it as the rule.

2.  **InformationConcept:** This is the specific *piece of information* that a `Requirement` needs, or that an `Evidence` provides.
  *   *Analogy:* The specific blank field on a form ("Date of Birth", "City Name", "Annual Income").
  *   *Example (Youth Pass):* To check the "Under 26" requirement, we need the "Date of Birth". "Date of Birth" is the `InformationConcept`.
  *   *CCCEV Definition:* "[Piece of information that the Evidence provides or the Requirement needs.](https://semiceu.github.io/CCCEV/releases/2.00/#Information%20Concept)"
  *   **DataUnit:** Every `InformationConcept` has a specific *type* of data, called a `DataUnit`. This tells us if the information is a date, a number, text, true/false, etc.
    *   *Example (Youth Pass):* The `InformationConcept` "Date of Birth" has a `DataUnit` of "Date". An `InformationConcept` like "Age" would have a `DataUnit` of "Number".
    *   *CCCEV Reference:* Although not a direct CCCEV class, it aligns with the `InformationConcept.type` and expected value format. In `connect-cccev`, `DataUnit` provides this structure.

3.  **Evidence:** This is the actual *proof* or *answer* provided to satisfy a `Requirement`.
  *   *Analogy:* The document you upload (ID scan, certificate PDF) or the answer you type into a form.
  *   *Example (Youth Pass):* A scan of your national ID card or passport is the `Evidence`. This document contains the "Date of Birth" (`InformationConcept`) needed to verify the "Under 26" (`Requirement`).
  *   *CCCEV Definition:* "[Proof that a Requirement is met.](https://semiceu.github.io/CCCEV/releases/2.00/#Evidence)"


## Domain
![Drag Racing](https://semiceu.github.io/CCCEV/releases/2.00/html/overview.jpg)


## Request score computation

Each InformationConcept has its own score. The total score of a request is the average score of all its
InformationConcepts.

**Computation rules per field**

- Score value goes from 0 to 100 included
- A field with an auto-computed value does not have a score
- Having no value blocks the score to 0, no matter if some other conditions are verified
- Having a value gives +50 score
- The rest is computed according to the provided evidences for the field (from 0 to 50 included)
    - Each EvidenceTypeList has its own score
    - The final score corresponds to the highest amongst the EvidenceTypeLists
    - The score of an ETL is `(numberOfFulfilledEvidenceTypes / totalEvidenceTypes) * 50`
