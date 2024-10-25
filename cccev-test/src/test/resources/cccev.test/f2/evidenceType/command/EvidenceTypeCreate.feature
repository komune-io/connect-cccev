Feature: EvidenceTypeCreate

  Scenario: I want to create a simple evidence type
    When I create an evidence type
    Then The evidence type should be created

  Scenario: I want to create an evidence type supporting information concepts
    Given A data unit is created
    And Some information concepts are created:
      | identifier |
      | ic1        |
      | ic2        |
    When I create an evidence type:
      | name   | concepts |
      | blblbl | ic1, ic2 |
    Then The evidence type should be created

  Scenario: I want to receive an error when creating an evidence type with a non-existing information concept
    Given A data unit is created
    And An information concept is created:
      | identifier |
      | ic1        |
    When I create an evidence type:
      | name   | concepts |
      | blblbl | ic1, ic2 |
    Then An exception should be thrown:
      | code |
      | 404  |
