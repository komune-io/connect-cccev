#Feature: CertificationAddEvidence
#
#  Scenario: I want to add evidences to a certification
#    Given An evidence type is created:
#      | identifier |
#      | et1        |
#    And A certification is created
#    When I add an evidence to the certification:
#      | evidence | name  | isConformantTo |
#      | e1       | proof | et1            |
#    And I add an evidence to the certification:
#      | evidence | name             | isConformantTo |
#      | e2       | proof_but_better | et1            |
#    Then The certifications should contain the evidences:
#      | evidence | name             | isConformantTo |
#      | e1       | proof            | et1            |
#      | e2       | proof_but_better | et1            |
