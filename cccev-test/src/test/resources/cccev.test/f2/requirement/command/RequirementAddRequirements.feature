Feature: RequirementAddRequirements

  Scenario: I want to add a sub-requirement to a requirement
    Given Some requirements are created:
      | identifier |
      | r1         |
      | r2         |
      | r3         |
    When I add sub-requirements to the requirement:
      | requirements |
      | r1, r2       |
    Then The requirement should contain the sub-requirements

  Scenario: I don't want to get an OptimisticLockException when adding two times the same sub-requirement to a requirement
    Given Some requirements are created:
      | identifier | hasRequirement |
      | r1         |                |
      | r2         | r1             |
    When I add sub-requirements to the requirement:
      | identifier | requirements |
      | r2         | r1           |
    Then The requirement should contain the sub-requirements
