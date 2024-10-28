pluginManagement {
	repositories {
		gradlePluginPortal()
		mavenCentral()
		maven { url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots") }
	}
}

rootProject.name = "connect-cccev"

include(
	"cccev-dsl:cccev-dsl-model",
//	"cccev-dsl:cccev-dsl-client",
)

include(
	"api-commons",
	"api-config",
	"api-gateway",
	"cccev-client",
	"cccev-core",
	"cccev-test",
)

include(
	"cccev-f2:cccev-f2-api",
	"cccev-f2:cccev-f2-domain",
)

include(
	"cccev-infra:fs",
	"cccev-infra:neo4j",
)
