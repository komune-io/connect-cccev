Feature: EvidenceTypeListAddEvidenceTypes

  Scenario: I want to add evidence types to an evidence type list
    Given Some evidence types are created:
      | identifier |
      | et1        |
      | et2        |
    And An evidence type list is created
    When I add evidence types to the evidence type list:
      | evidenceTypes |
      | et1, et2      |
    Then The evidence type list should contain the evidence types
