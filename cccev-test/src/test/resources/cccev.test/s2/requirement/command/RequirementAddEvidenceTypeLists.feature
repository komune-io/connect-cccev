Feature: RequirementAddEvidenceTypeLists

  Scenario: I want to add evidence type lists to a requirement
    Given Some evidence type lists are created:
      | identifier |
      | etl1       |
      | etl2       |
    And A requirement is created
    When I add evidence type lists to the requirement:
      | evidenceTypeLists |
      | etl1, etl2        |
    Then The requirement should contain the evidence type lists
