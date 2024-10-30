Feature: CertificationFillProcess

  Background:
    Given A data unit is created:
      | identifier | name   | notation | type   |
      | u_budget   | budget | €        | NUMBER |
    And Some information concepts are created:
      | identifier              | name            | unit     | expressionOfExpectedValue                                     | dependsOn                                              |
      | ic_budget_q1            | Budget Q1       | u_budget |                                                               |                                                        |
      | ic_budget_q2            | Budget Q2       | u_budget |                                                               |                                                        |
      | ic_budget_q3            | Budget Q3       | u_budget |                                                               |                                                        |
      | ic_budget_q4            | Budget Q4       | u_budget |                                                               |                                                        |
      | ic_budget_year_current  | Budget Year N   | u_budget | #ic_budget_q1 + #ic_budget_q2 + #ic_budget_q3 + #ic_budget_q4 | ic_budget_q1, ic_budget_q2, ic_budget_q3, ic_budget_q4 |
      | ic_budget_year_previous | Budget Year N-1 | u_budget |                                                               |                                                        |
      | ic_budget_year_next     | Budget Year N+1 | u_budget |                                                               |                                                        |
    And An evidence type is created:
      | identifier               | concepts                                               |
      | et_year_financial_report | ic_budget_q1, ic_budget_q2, ic_budget_q3, ic_budget_q4 |
    And Some requirements are created:
      | identifier                         | kind        | name             | hasConcept              | hasEvidenceType          | hasRequirement                                                       | validatingCondition                                | validatingConditionDependencies                 | evidenceValidatingCondition       |
      | r_budget_q1                        | INFORMATION | Budget Q1        | ic_budget_q1            |                          |                                                                      |                                                    |                                                 |                                   |
      | r_budget_q2                        | INFORMATION | Budget Q2        | ic_budget_q2            |                          |                                                                      |                                                    |                                                 |                                   |
      | r_budget_q3                        | INFORMATION | Budget Q3        | ic_budget_q3            |                          |                                                                      |                                                    |                                                 |                                   |
      | r_budget_q4                        | INFORMATION | Budget Q4        | ic_budget_q4            |                          |                                                                      |                                                    |                                                 |                                   |
      | r_budget_year_current_satisfactory | CONSTRAINT  | Budget Year N    | ic_budget_year_current  |                          |                                                                      | #ic_budget_year_current > 10000                    | ic_budget_year_current                          |                                   |
      | r_budget_year_current              | INFORMATION | Budget Year N    | ic_budget_year_current  |                          | r_budget_year_current_satisfactory                                   |                                                    |                                                 |                                   |
      | r_budget_year_previous             | INFORMATION | Budget Year N-1  | ic_budget_year_previous |                          |                                                                      |                                                    |                                                 |                                   |
      | r_budget_year_next                 | INFORMATION | Budget Year N+1  | ic_budget_year_next     |                          |                                                                      |                                                    |                                                 |                                   |
      | r_budget_has_grown                 | CONSTRAINT  | Budget has grown |                         |                          | r_budget_year_previous, r_budget_year_current                        | #ic_budget_year_current > #ic_budget_year_previous | ic_budget_year_current, ic_budget_year_previous |                                   |
      | r_budget_will_grow                 | CONSTRAINT  | Budget will grow |                         |                          | r_budget_year_current, r_budget_year_next                            | #ic_budget_year_next > #ic_budget_year_current     | ic_budget_year_next, ic_budget_year_current     |                                   |
      | r_budget_is_fine                   | CRITERION   | Budget is fine   |                         |                          | r_budget_has_grown, r_budget_will_grow                               |                                                    |                                                 |                                   |
      | r_budget                           | CRITERION   | Budget           |                         | et_year_financial_report | r_budget_q1, r_budget_q2, r_budget_q3, r_budget_q4, r_budget_is_fine |                                                    |                                                 | #et_year_financial_report != null |

  Scenario: Budget: Creation
    Given A certification is created:
      | identifier | requirements |
      | c          | r_budget     |
    Then The requirement certifications should be created:
      | requirement                        | subCertifications                                                    | isEnabled | isValidated | hasAllValues | areEvidencesProvided | isFulfilled |
      | r_budget_q1                        |                                                                      | true      | true        | false        | true                 | false       |
      | r_budget_q2                        |                                                                      | true      | true        | false        | true                 | false       |
      | r_budget_q3                        |                                                                      | true      | true        | false        | true                 | false       |
      | r_budget_q4                        |                                                                      | true      | true        | false        | true                 | false       |
      | r_budget_year_current_satisfactory |                                                                      | true      | false       | false        | true                 | false       |
      | r_budget_year_current              | r_budget_year_current_satisfactory                                   | true      | true        | false        | true                 | false       |
      | r_budget_year_previous             |                                                                      | true      | true        | false        | true                 | false       |
      | r_budget_year_next                 |                                                                      | true      | true        | false        | true                 | false       |
      | r_budget_has_grown                 | r_budget_year_previous, r_budget_year_current                        | true      | false       | true         | true                 | false       |
      | r_budget_will_grow                 | r_budget_year_current, r_budget_year_next                            | true      | false       | true         | true                 | false       |
      | r_budget_is_fine                   | r_budget_has_grown, r_budget_will_grow                               | true      | true        | true         | true                 | false       |
      | r_budget                           | r_budget_q1, r_budget_q2, r_budget_q3, r_budget_q4, r_budget_is_fine | true      | true        | true         | false                | false       |

  Scenario: Budget: Values only
    Given A certification is created:
      | identifier | requirements |
      | c          | r_budget     |
    When I fill values in the certification:
      | identifier             | concept                 | value  |
      | v_budget_q1            | ic_budget_q1            | 666    |
      | v_budget_q2            | ic_budget_q2            | 4000   |
      | v_budget_q3            | ic_budget_q3            | 3000   |
      | v_budget_q4            | ic_budget_q4            | 5000   |
      | v_budget_year_previous | ic_budget_year_previous | 100000 |
    Then The supported values should be created:
      | identifier             | concept                 | value   | autogenerated |
      | v_budget_q1            | ic_budget_q1            | 666     | false         |
      | v_budget_q2            | ic_budget_q2            | 4000    | false         |
      | v_budget_q3            | ic_budget_q3            | 3000    | false         |
      | v_budget_q4            | ic_budget_q4            | 5000    | false         |
      | v_budget_year_current  | ic_budget_year_current  | 12666.0 | true          |
      | v_budget_year_previous | ic_budget_year_previous | 100000  | false         |
    Then The requirement certifications should be created:
      | requirement                        | isEnabled | isValidated | hasAllValues | areEvidencesProvided | isFulfilled |
      | r_budget_q1                        | true      | true        | true         | true                 | true        |
      | r_budget_q2                        | true      | true        | true         | true                 | true        |
      | r_budget_q3                        | true      | true        | true         | true                 | true        |
      | r_budget_q4                        | true      | true        | true         | true                 | true        |
      | r_budget_year_current_satisfactory | true      | true        | true         | true                 | true        |
      | r_budget_year_current              | true      | true        | true         | true                 | true        |
      | r_budget_year_previous             | true      | true        | true         | true                 | true        |
      | r_budget_year_next                 | true      | true        | false        | true                 | false       |
      | r_budget_has_grown                 | true      | false       | true         | true                 | false       |
      | r_budget_will_grow                 | true      | false       | true         | true                 | false       |
      | r_budget_is_fine                   | true      | true        | true         | true                 | false       |
      | r_budget                           | true      | true        | true         | false                | false       |
    When I fill values in the certification:
      | identifier         | concept             | value  |
      | v_budget_year_next | ic_budget_year_next | 200000 |
    Then The supported values should be created:
      | identifier         | concept             | value  |
      | v_budget_year_next | ic_budget_year_next | 200000 |
    Then The requirement certifications should be created:
      | requirement                        | isEnabled | isValidated | hasAllValues | areEvidencesProvided | isFulfilled |
      | r_budget_q1                        | true      | true        | true         | true                 | true        |
      | r_budget_q2                        | true      | true        | true         | true                 | true        |
      | r_budget_q3                        | true      | true        | true         | true                 | true        |
      | r_budget_q4                        | true      | true        | true         | true                 | true        |
      | r_budget_year_current_satisfactory | true      | true        | true         | true                 | true        |
      | r_budget_year_current              | true      | true        | true         | true                 | true        |
      | r_budget_year_previous             | true      | true        | true         | true                 | true        |
      | r_budget_year_next                 | true      | true        | true         | true                 | true        |
      | r_budget_has_grown                 | true      | false       | true         | true                 | false       |
      | r_budget_will_grow                 | true      | true        | true         | true                 | true        |
      | r_budget_is_fine                   | true      | true        | true         | true                 | false       |
      | r_budget                           | true      | true        | true         | false                | false       |
    When I fill values in the certification:
      | concept      | value  |
      | ic_budget_q4 | 100000 |
    Then The supported values should be created:
      | identifier            | concept                | value    |
      | v_budget_q4           | ic_budget_q4           | 100000   |
      | v_budget_year_current | ic_budget_year_current | 107666.0 |
    Then The requirement certifications should be created:
      | requirement                        | isEnabled | isValidated | hasAllValues | areEvidencesProvided | isFulfilled |
      | r_budget_q1                        | true      | true        | true         | true                 | true        |
      | r_budget_q2                        | true      | true        | true         | true                 | true        |
      | r_budget_q3                        | true      | true        | true         | true                 | true        |
      | r_budget_q4                        | true      | true        | true         | true                 | true        |
      | r_budget_year_current_satisfactory | true      | true        | true         | true                 | true        |
      | r_budget_year_current              | true      | true        | true         | true                 | true        |
      | r_budget_year_previous             | true      | true        | true         | true                 | true        |
      | r_budget_year_next                 | true      | true        | true         | true                 | true        |
      | r_budget_has_grown                 | true      | true        | true         | true                 | true        |
      | r_budget_will_grow                 | true      | true        | true         | true                 | true        |
      | r_budget_is_fine                   | true      | true        | true         | true                 | true        |
      | r_budget                           | true      | true        | true         | false                | false       |

  Scenario: Budget: Evidences only
    Given A certification is created:
      | identifier | requirements |
      | c          | r_budget     |
    When I add an evidence to the certification:
      | identifier | evidenceType             | path                             |
      | e1         | et_year_financial_report | certification/budget/evidence/e1 |
    Then The evidences should be created
    And The requirement certifications should be created:
      | requirement | areEvidencesProvided |
      | r_budget    | true                 |

  Scenario: Budget: Values then evidences
    Given A certification is created:
      | identifier | requirements |
      | c          | r_budget     |
    When I fill values in the certification:
      | identifier             | concept                 | value  |
      | v_budget_q1            | ic_budget_q1            | 666    |
      | v_budget_q2            | ic_budget_q2            | 4000   |
      | v_budget_q3            | ic_budget_q3            | 3000   |
      | v_budget_q4            | ic_budget_q4            | 5000   |
      | v_budget_year_previous | ic_budget_year_previous | 100000 |
    Then The supported values should be created:
      | identifier             | evidences |
      | v_budget_q1            |           |
      | v_budget_q2            |           |
      | v_budget_q3            |           |
      | v_budget_q4            |           |
      | v_budget_year_previous |           |
    When I add an evidence to the certification:
      | identifier | evidenceType             | path                             |
      | e1         | et_year_financial_report | certification/budget/evidence/e1 |
    Then The evidences should be created
    And The supported values should be created:
      | identifier             | evidences |
      | v_budget_q1            | e1        |
      | v_budget_q2            | e1        |
      | v_budget_q3            | e1        |
      | v_budget_q4            | e1        |
      | v_budget_year_previous |           |

  Scenario: Budget: Evidences then values
    Given A certification is created:
      | identifier | requirements |
      | c          | r_budget     |
    When I add an evidence to the certification:
      | identifier | evidenceType             | path                             |
      | e1         | et_year_financial_report | certification/budget/evidence/e1 |
    Then The evidences should be created
    When I fill values in the certification:
      | identifier             | concept                 | value  |
      | v_budget_q1            | ic_budget_q1            | 666    |
      | v_budget_q2            | ic_budget_q2            | 4000   |
      | v_budget_q3            | ic_budget_q3            | 3000   |
      | v_budget_q4            | ic_budget_q4            | 5000   |
      | v_budget_year_previous | ic_budget_year_previous | 100000 |
    And The supported values should be created:
      | identifier             | evidences |
      | v_budget_q1            | e1        |
      | v_budget_q2            | e1        |
      | v_budget_q3            | e1        |
      | v_budget_q4            | e1        |
      | v_budget_year_previous |           |
