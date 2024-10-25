Feature: RequirementCreate

  Background:
    Given A data unit is created
    And An information concept is created:
      | identifier |
      | c1         |
#    And Some evidence types are created:
#      | identifier |
#      | et1        |
#      | et2        |
#      | et3        |
#    And An evidence type list is created:
#      | identifier | specifiesEvidenceType |
#      | etl1       | et1, et2, et3         |
#    And A framework is created:
#      | identifier |
#      | f1         |

  Scenario: I want to create a requirement
    When I create a requirement:
      | type     | hasConcept |
      | activity | c1         |
    Then The requirement should be created

  Scenario: I want to create a requirement with a sub-requirement
    Given A requirement is created:
      | identifier | type | hasConcept | hasEvidenceTypeList |
      | r1         | step | c1         | etl1                |
    When I create a requirement:
      | type     | hasRequirement |
      | activity | r1             |
    Then The requirement should be created

  Scenario: tmp
    Given Some requirements are created:
      | identifier | type     | hasRequirement | hasConcept | hasEvidenceTypeList |
      | r1         | step     |                | c1         | etl1                |
      | r2         | activity | r1             |            |                     |
      | r3         | activity | r2             |            |                     |
      | r4         | activity |                |            |                     |
    When I create a requirement:
      | type     | hasRequirement |
      | activity | r3, r2         |
    Then The requirement should be created
