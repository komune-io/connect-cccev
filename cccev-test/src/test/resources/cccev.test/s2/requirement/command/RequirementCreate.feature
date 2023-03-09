Feature: RequirementCreate

  Background:
    Given A data unit is created
    And An information concept is created:
      | identifier |
      | c1         |
    And Some evidence types are created:
      | identifier |
      | et1        |
      | et2        |
      | et3        |
    And An evidence type list is created:
      | identifier | specifiesEvidenceType |
      | etl1       | et1, et2, et3         |

  Scenario: I want to create a requirement
    When I create a requirement:
      | hasConcept | hasEvidenceTypeList |
      | c1         | etl1                |
    Then The requirement should be created

  Scenario: I want to create a requirement with a sub-requirement
    Given A requirement is created:
      | identifier | hasConcept | hasEvidenceTypeList |
      | r1         | c1         | etl1                |
    When I create a requirement:
      | hasRequirement |
      | r1             |
    Then The requirement should be created
