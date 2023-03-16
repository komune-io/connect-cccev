Feature: RequirementAddConcepts

  Scenario: I want to add information concepts to a requirement
    Given A data unit is created
    And Some information concepts are created:
      | identifier |
      | ic1        |
      | ic2        |
    And A requirement is created
    When I add information concepts to the requirement:
      | concepts |
      | ic1, ic2 |
    Then The requirement should contain the information concepts
