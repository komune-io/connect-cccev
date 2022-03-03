pluginManagement {
	repositories {
		gradlePluginPortal()
		maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
	}
}

rootProject.name = "cccev-cee"

include(
	"cccev-dsl:cccev-dsl-core",
	"cccev-dsl:cccev-dsl-dto",
)
include(
	"api-gateway",
	"api-commons",
	"cccev-api",
	"cccev-f2",
	"cccev-test"
)
include(
	"cccev-s2:request:request-app",
	"cccev-s2:request:request-domain",
	"cccev-s2:request:request-tasks"
)
include(
	"cccev-s2:requirement:requirement-app",
	"cccev-s2:requirement:requirement-domain"
)
include(
	"cccev-s2:s2-spring-boot-starter-automate-jena",
	"cccev-s2:s2-spring-boot-starter-automate-rdf4j"
)
include(
	"cccev-bubble:cccev-bubble-core",
)
