Feature: RequirementRemoveEvidenceTypeLists

  Scenario: I want to remove evidence type lists from a requirement
    Given Some evidence type lists are created:
      | identifier |
      | etl1       |
      | etl2       |
    And A requirement is created:
      | hasEvidenceTypeList |
      | etl1, etl2          |
    When I remove evidence type lists from the requirement:
      | evidenceTypeLists |
      | etl1              |
    Then The requirement should not contain the evidence type lists
    And The requirement should contain the evidence type lists:
      | evidenceTypeLists |
      | etl2              |
