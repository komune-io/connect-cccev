Feature: EvidenceTypeListRemoveEvidenceTypes

  Scenario: I want to remove evidence types to an evidence type list
    Given Some evidence types are created:
      | identifier |
      | et1        |
      | et2        |
    And An evidence type list is created:
      | specifiesEvidenceType |
      | et1, et2              |
    When I remove evidence types from the evidence type list:
      | evidenceTypes |
      | et1           |
    Then The evidence type list should not contain the evidence types
    Then The evidence type list should contain the evidence types:
      | identifier |
      | et2        |
