pluginManagement {
	repositories {
		gradlePluginPortal()
		mavenCentral()
		maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
		maven { url = uri("https://repo.spring.io/milestone") }
	}
}

rootProject.name = "cccev"

include(
	"cccev-dsl:cccev-dsl-model",
	"cccev-dsl:cccev-dsl-client",
)
include(
	"api-commons",
	"api-config",
	"api-gateway",
	"cccev-f2",
	"cccev-test"
)

include(
	"cccev-core"
)

include(
	"cccev-f2:cccev-f2-commons",
	"cccev-f2:certification-f2:cccev-certification-f2-api",
	"cccev-f2:certification-f2:cccev-certification-f2-client",
	"cccev-f2:certification-f2:cccev-certification-f2-domain",
	"cccev-f2:concept-f2:cccev-concept-f2-api",
	"cccev-f2:concept-f2:cccev-concept-f2-client",
	"cccev-f2:concept-f2:cccev-concept-f2-domain",
	"cccev-f2:evidence-f2:cccev-evidence-f2-api",
	"cccev-f2:evidence-f2:cccev-evidence-f2-domain",
	"cccev-f2:evidence-type-f2:cccev-evidence-type-f2-api",
	"cccev-f2:evidence-type-f2:cccev-evidence-type-f2-client",
	"cccev-f2:evidence-type-f2:cccev-evidence-type-f2-domain",
	"cccev-f2:requirement-f2:cccev-requirement-f2-api",
	"cccev-f2:requirement-f2:cccev-requirement-f2-client",
	"cccev-f2:requirement-f2:cccev-requirement-f2-domain",
	"cccev-f2:unit-f2:cccev-unit-f2-api",
	"cccev-f2:unit-f2:cccev-unit-f2-client",
	"cccev-f2:unit-f2:cccev-unit-f2-domain",
)

include(
	"cccev-infra:fs",
	"cccev-infra:neo4j",
)

include(
	"cccev-projection:cccev-projection-api",
	"cccev-projection:cccev-projection-domain"
)

include(
	"cccev-s2:evidence-type:cccev-evidence-type-api",
	"cccev-s2:evidence-type:cccev-evidence-type-domain"
)
