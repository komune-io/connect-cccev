#Feature: CertificationStats
#
#  Background:
#    Given A data unit is created
#
#  Scenario: I want to compute the stats of one non-completed requirement
#    Given Some information concepts are created:
#      | identifier |
#      | ic1        |
#    And A requirement is created:
#      | identifier | hasConcept |
#      | r1         | ic1        |
#    And A certification is created:
#      | requirements |
#      | r1           |
#    When I fetch the certification
#    Then I should receive the requirement stats of the certification:
#      | requirement | completion |
#      | r1          | 0.0        |
#
#  Scenario: I want to compute the stats of one completed requirement
#    Given Some information concepts are created:
#      | identifier |
#      | ic1        |
#      | ic2        |
#    And A requirement is created:
#      | identifier | hasConcept |
#      | r1         | ic1, ic2   |
#    And A certification is created:
#      | requirements |
#      | r1           |
#    And Some values are added to the certification:
#      | concept | value  |
#      | ic1     | blblbl |
#    When I fetch the certification
#    Then I should receive the requirement stats of the certification:
#      | requirement | completion |
#      | r1          | 50.0       |
#
#  Scenario: I want to compute the stats of a requirement with an auto-computed information concept
#    Given Some information concepts are created:
#      | identifier | dependsOn |
#      | ic1        |           |
#      | ic2        |           |
#      | ic3        | ic1, ic2  |
#    And A requirement is created:
#      | identifier | hasConcept |
#      | r1         | ic3        |
#    And A certification is created:
#      | requirements |
#      | r1           |
#    And Some values are added to the certification:
#      | concept | value  |
#      | ic1     | blblbl |
#    When I fetch the certification
#    Then I should receive the requirement stats of the certification:
#      | requirement | completion |
#      | r1          | 50.0       |
#
#  Scenario: I want to compute the stats of a requirement with children
#    Given Some information concepts are created:
#      | identifier |
#      | ic1        |
#      | ic2        |
#      | ic3        |
#    And Some requirements are created:
#      | identifier | hasConcept | hasRequirement |
#      | r1         | ic1, ic2   |                |
#      | r2         | ic3        |                |
#      | r3         |            | r1, r2         |
#    And A certification is created:
#      | requirements |
#      | r3           |
#    And Some values are added to the certification:
#      | concept | value  |
#      | ic1     | blblbl |
#      | ic3     | 42     |
#    When I fetch the certification
#    Then I should receive the requirement stats of the certification:
#      | requirement | completion |
#      | r1          | 50.0       |
#      | r2          | 100.0      |
#      | r3          | 75.0       |
#
#  Scenario: I want to compute the stats of multiple requirements with children
#    Given Some information concepts are created:
#      | identifier |
#      | ic1        |
#      | ic2        |
#      | ic3        |
#      | ic4        |
#    And Some requirements are created:
#      | identifier | hasConcept | hasRequirement |
#      | r111       | ic1, ic2   |                |
#      | r112       | ic3        |                |
#      | r11        |            | r111, r112     |
#      | r121       | ic3        |                |
#      | r12        | ic2        | r121           |
#      | r1         |            | r11, r12       |
#      | r2         | ic4        |                |
#    And A certification is created:
#      | requirements |
#      | r1, r2       |
#    And Some values are added to the certification:
#      | concept | value  |
#      | ic1     | blblbl |
#      | ic3     | 42     |
#      | ic4     | 666    |
#    When I fetch the certification
#    Then I should receive the requirement stats of the certification:
#      | requirement | completion |
#      | r111        | 50.0       |
#      | r112        | 100.0      |
#      | r11         | 75.0       |
#      | r121        | 100.0      |
#      | r12         | 50.0       |
#      | r1          | 62.5       |
#      | r2          | 100.0      |
