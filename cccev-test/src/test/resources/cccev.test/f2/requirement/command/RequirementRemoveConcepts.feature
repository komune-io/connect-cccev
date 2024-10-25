Feature: RequirementRemoveConcepts

  Scenario: I want to remove information concepts from a requirement
    Given A data unit is created
    And Some information concepts are created:
      | identifier |
      | ic1        |
      | ic2        |
    And A requirement is created:
      | hasConcept |
      | ic1, ic2   |
    When I remove information concepts from the requirement:
      | concepts |
      | ic1      |
    Then The requirement should not contain the information concepts
    And The requirement should contain the information concepts:
      | concepts |
      | ic2      |
