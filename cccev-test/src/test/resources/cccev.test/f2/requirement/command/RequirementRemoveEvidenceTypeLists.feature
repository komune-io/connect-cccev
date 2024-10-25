Feature: RequirementRemoveEvidenceTypes

  Scenario: I want to remove evidence types from a requirement
    Given Some evidence types are created:
      | identifier |
      | et1       |
      | et2       |
    And A requirement is created:
      | hasEvidenceType |
      | et1, et2      |
    When I remove evidence types from the requirement:
      | evidenceTypes |
      | et1          |
    Then The requirement should not contain the evidence types
    And The requirement should contain the evidence types:
      | evidenceTypes |
      | et2           |
