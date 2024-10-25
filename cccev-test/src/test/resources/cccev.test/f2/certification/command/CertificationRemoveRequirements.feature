#Feature: CertificationRemoveRequirements
#
#  Scenario: I want to remove requirements from a certification
#    Given Some requirements are created:
#      | identifier |
#      | r1         |
#      | r2         |
#      | r3          |
#    And A certification is created:
#      | requirements |
#      | r1, r2, r3   |
#    When I remove requirements from the certification:
#      | requirements |
#      | r1, r2       |
#    Then The certification should not contain the requirements:
#      | requirements |
#      | r1, r2       |
#    And The certification should contain the requirements:
#      | requirements |
#      | r3           |
