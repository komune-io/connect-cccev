Feature: CertificationFillValues

  Scenario: Budget
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
    And Some requirements are created:
      | identifier                         | kind        | name             | hasConcept              | hasRequirement                                                       | validatingCondition                                | validatingConditionDependencies                 |
      | r_budget_q1                        | INFORMATION | Budget Q1        | ic_budget_q1            |                                                                      |                                                    |                                                 |
      | r_budget_q2                        | INFORMATION | Budget Q2        | ic_budget_q2            |                                                                      |                                                    |                                                 |
      | r_budget_q3                        | INFORMATION | Budget Q3        | ic_budget_q3            |                                                                      |                                                    |                                                 |
      | r_budget_q4                        | INFORMATION | Budget Q4        | ic_budget_q4            |                                                                      |                                                    |                                                 |
      | r_budget_year_current_satisfactory | CONSTRAINT  | Budget Year N    | ic_budget_year_current  |                                                                      | #ic_budget_year_current > 10000                    | ic_budget_year_current                          |
      | r_budget_year_current              | INFORMATION | Budget Year N    | ic_budget_year_current  | r_budget_year_current_satisfactory                                   |                                                    |                                                 |
      | r_budget_year_previous             | INFORMATION | Budget Year N-1  | ic_budget_year_previous |                                                                      |                                                    |                                                 |
      | r_budget_year_next                 | INFORMATION | Budget Year N+1  | ic_budget_year_next     |                                                                      |                                                    |                                                 |
      | r_budget_has_grown                 | CONSTRAINT  | Budget has grown |                         | r_budget_year_previous, r_budget_year_current                        | #ic_budget_year_current > #ic_budget_year_previous | ic_budget_year_current, ic_budget_year_previous |
      | r_budget_will_grow                 | CONSTRAINT  | Budget will grow |                         | r_budget_year_current, r_budget_year_next                            | #ic_budget_year_next > #ic_budget_year_current     | ic_budget_year_next, ic_budget_year_current     |
      | r_budget_is_fine                   | CRITERION   | Budget is fine   |                         | r_budget_has_grown, r_budget_will_grow                               |                                                    |                                                 |
      | r_budget                           | CRITERION   | Budget           |                         | r_budget_q1, r_budget_q2, r_budget_q3, r_budget_q4, r_budget_is_fine |                                                    |                                                 |
    And A certification is created:
      | identifier | requirements |
      | c          | r_budget     |
    Then The requirement certifications should be created:
      | certification | requirement                        | subCertifications                                                    | isEnabled | isValidated | hasAllValues | isFulfilled |
      | c             | r_budget_q1                        |                                                                      | true      | true        | false        | false       |
      | c             | r_budget_q2                        |                                                                      | true      | true        | false        | false       |
      | c             | r_budget_q3                        |                                                                      | true      | true        | false        | false       |
      | c             | r_budget_q4                        |                                                                      | true      | true        | false        | false       |
      | c             | r_budget_year_current_satisfactory |                                                                      | true      | false       | false        | false       |
      | c             | r_budget_year_current              | r_budget_year_current_satisfactory                                   | true      | true        | false        | false       |
      | c             | r_budget_year_previous             |                                                                      | true      | true        | false        | false       |
      | c             | r_budget_year_next                 |                                                                      | true      | true        | false        | false       |
      | c             | r_budget_has_grown                 | r_budget_year_previous, r_budget_year_current                        | true      | false       | true         | false       |
      | c             | r_budget_will_grow                 | r_budget_year_current, r_budget_year_next                            | true      | false       | true         | false       |
      | c             | r_budget_is_fine                   | r_budget_has_grown, r_budget_will_grow                               | true      | true        | true         | false       |
      | c             | r_budget                           | r_budget_q1, r_budget_q2, r_budget_q3, r_budget_q4, r_budget_is_fine | true      | true        | true         | false       |
    When I fill values in the certification:
      | identifier | concept                 | value  |
      | c          | ic_budget_q1            | 666    |
      | c          | ic_budget_q2            | 4000   |
      | c          | ic_budget_q3            | 3000   |
      | c          | ic_budget_q4            | 5000   |
      | c          | ic_budget_year_previous | 100000 |
    Then The requirement certifications should be created:
      | certification | requirement                        | isEnabled | isValidated | hasAllValues | isFulfilled |
      | c             | r_budget_q1                        | true      | true        | true         | true        |
      | c             | r_budget_q2                        | true      | true        | true         | true        |
      | c             | r_budget_q3                        | true      | true        | true         | true        |
      | c             | r_budget_q4                        | true      | true        | true         | true        |
      | c             | r_budget_year_current_satisfactory | true      | true        | true         | true        |
      | c             | r_budget_year_current              | true      | true        | true         | true        |
      | c             | r_budget_year_previous             | true      | true        | true         | true        |
      | c             | r_budget_year_next                 | true      | true        | false        | false       |
      | c             | r_budget_has_grown                 | true      | false       | true         | false       |
      | c             | r_budget_will_grow                 | true      | false       | true         | false       |
      | c             | r_budget_is_fine                   | true      | true        | true         | false       |
      | c             | r_budget                           | true      | true        | true         | false       |
    When I fill values in the certification:
      | identifier | concept             | value  |
      | c          | ic_budget_year_next | 200000 |
    Then The requirement certifications should be created:
      | certification | requirement                        | isEnabled | isValidated | hasAllValues | isFulfilled |
      | c             | r_budget_q1                        | true      | true        | true         | true        |
      | c             | r_budget_q2                        | true      | true        | true         | true        |
      | c             | r_budget_q3                        | true      | true        | true         | true        |
      | c             | r_budget_q4                        | true      | true        | true         | true        |
      | c             | r_budget_year_current_satisfactory | true      | true        | true         | true        |
      | c             | r_budget_year_current              | true      | true        | true         | true        |
      | c             | r_budget_year_previous             | true      | true        | true         | true        |
      | c             | r_budget_year_next                 | true      | true        | true         | true        |
      | c             | r_budget_has_grown                 | true      | false       | true         | false       |
      | c             | r_budget_will_grow                 | true      | true        | true         | true        |
      | c             | r_budget_is_fine                   | true      | true        | true         | false       |
      | c             | r_budget                           | true      | true        | true        | false       |
    When I fill values in the certification:
      | identifier | concept      | value  |
      | c          | ic_budget_q4 | 100000 |
    Then The requirement certifications should be created:
      | certification | requirement                        | isEnabled | isValidated | hasAllValues | isFulfilled |
      | c             | r_budget_q1                        | true      | true        | true         | true        |
      | c             | r_budget_q2                        | true      | true        | true         | true        |
      | c             | r_budget_q3                        | true      | true        | true         | true        |
      | c             | r_budget_q4                        | true      | true        | true         | true        |
      | c             | r_budget_year_current_satisfactory | true      | true        | true         | true        |
      | c             | r_budget_year_current              | true      | true        | true         | true        |
      | c             | r_budget_year_previous             | true      | true        | true         | true        |
      | c             | r_budget_year_next                 | true      | true        | true         | true        |
      | c             | r_budget_has_grown                 | true      | true        | true         | true        |
      | c             | r_budget_will_grow                 | true      | true        | true         | true        |
      | c             | r_budget_is_fine                   | true      | true        | true         | true        |
      | c             | r_budget                           | true      | true        | true         | true        |
