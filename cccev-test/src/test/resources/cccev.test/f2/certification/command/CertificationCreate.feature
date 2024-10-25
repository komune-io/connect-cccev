Feature: CertificationCreate

  Scenario: I want to create a certification
    Given Some requirements are created:
      | identifier |
      | r1         |
      | r2         |
      | r3         |
    When I create a certification:
      | identifier | requirements |
      | c          | r1, r2       |
    Then The requirement certifications should be created:
      | certification | requirement |
      | c             | r1          |
      | c             | r2          |

  Scenario: I want to create a certification with nested requirements
    Given Some requirements are created:
      | identifier | hasRequirement |
      | r1         |                |
      | r2         |                |
      | r3         | r1, r2         |
    When I create a certification:
      | identifier | requirements |
      | c          | r3           |
    Then The requirement certifications should be created:
      | certification | requirement | subCertifications |
      | c             | r3          | r1, r2            |
      | c             | r1          |                   |
      | c             | r2          |                   |

  Scenario: I want to receive an error when creating a certification with a non-existing requirement
    When I create a certification:
      | identifier | requirements |
      | c          | fake         |
    Then An exception should be thrown:
      | code |
      | 404  |

  Scenario: I want to receive an error when creating a certification with an existing identifier
    Given A certification is created:
      | identifier |
      | c          |
    When I create a certification:
      | identifier |
      | c          |
    Then An exception should be thrown:
      | code |
      | 409  |
