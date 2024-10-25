Feature: RequirementRemoveRequirements

  Scenario: I want to remove a sub-requirement from a requirement
    Given Some requirements are created:
      | identifier | hasRequirement |
      | r1         |                |
      | r2         |                |
      | r3         | r1, r2         |
    When I remove sub-requirements from the requirement:
      | requirements |
      | r1           |
    Then The requirement should not contain the sub-requirements
    And The requirement should contain the sub-requirements:
      | identifier | requirements |
      | r3         | r2           |
