#Feature: CertificationRemoveEvidence
#
#  Scenario: I want to remove evidence from a request
#    Given An evidence type is created:
#      | identifier |
#      | et1        |
#    And A certification is created
#    And Some evidences are added to the certifications:
#      | evidence | name             | isConformantTo |
#      | e1       | proof            | et1            |
#      | e2       | proof_but_better | et1            |
#    When I remove an evidence from the certification:
#      | evidence |
#      | e1       |
#    Then The certification should not contain the evidence:
#      | evidence |
#      | e1       |
#    And The certification should contain the evidence:
#      | evidence |
#      | e2       |
