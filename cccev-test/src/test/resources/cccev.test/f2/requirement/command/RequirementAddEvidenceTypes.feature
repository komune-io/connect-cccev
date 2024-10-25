Feature: RequirementAddEvidenceTypes

  Scenario: I want to add evidence types to a requirement
    Given Some evidence types are created:
      | identifier |
      | et1       |
      | et2       |
    And A requirement is created
    When I add evidence types to the requirement:
      | evidenceTypes |
      | et1, et2      |
    Then The requirement should contain the evidence types
