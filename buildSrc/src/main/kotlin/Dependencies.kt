import io.komune.gradle.dependencies.FixersDependencies
import io.komune.gradle.dependencies.FixersPluginVersions
import io.komune.gradle.dependencies.FixersVersions
import io.komune.gradle.dependencies.Scope
import io.komune.gradle.dependencies.add

object Framework {
	val fixers = FixersPluginVersions.fixers
	val connect = "0.15.0-SNAPSHOT"
}

object PluginVersions {
	const val kotlin = FixersPluginVersions.kotlin
	const val springBoot = FixersPluginVersions.springBoot //"3.2.2"
	val fixers = Framework.fixers
	val d2 = Framework.fixers
}

object Versions {
	val fs = Framework.connect
	val s2 =  Framework.fixers
	val springBoot = PluginVersions.springBoot

	const val cucumber = FixersVersions.Test.cucumber
	const val ktor = FixersVersions.Kotlin.ktor
	const val awaitility = "4.1.1"
	const val datafaker = "1.8.1"
	const val javaSnapshotTesting = "4.0.7"
	const val jsonAssert = "1.5.1"
	const val neo4jOgm = "4.0.3"
}

object Repo {
	val snapshot: List<String> = listOf(
		// For fixers
		"https://s01.oss.sonatype.org/content/repositories/snapshots",
	)
}

object Dependencies {
	object Jvm {
		fun s2Bdd(scope: Scope) = scope.add(
			"io.komune.s2:s2-test-bdd:${Versions.s2}",
			"org.springframework.boot:spring-boot-starter-test:${PluginVersions.springBoot}"
		).also(::cucumber)
			.also(::junit)

		fun f2(scope: Scope) = scope.add(
			"io.komune.f2:f2-spring-boot-starter-function:${Framework.fixers}",
			"io.komune.f2:f2-spring-boot-exception-http:${Framework.fixers}"
		)

		fun f2Http(scope: Scope) = scope.add(
			"io.komune.f2:f2-spring-boot-starter-function-http:${Framework.fixers}",
			"io.komune.f2:f2-spring-boot-openapi:${Framework.fixers}"
		)

		fun s2EventSouringBc(scope: Scope) = scope.add(
			"io.komune.s2:s2-spring-boot-starter-sourcing-ssm:${Versions.s2}",
			"io.komune.s2:s2-spring-boot-starter-utils-logger:${Versions.s2}"
		)
		fun jackson(scope: Scope) = FixersDependencies.Jvm.Json.jackson(scope)
		fun coroutines(scope: Scope) = FixersDependencies.Jvm.Kotlin.coroutines(scope)
		fun cucumber(scope: Scope) = FixersDependencies.Jvm.Test.cucumber(scope).also {
			scope.add("io.cucumber:cucumber-spring:${Versions.cucumber}")
		}
		fun junit(scope: Scope) = FixersDependencies.Jvm.Test.junit(scope).also {
			scope.add("org.awaitility:awaitility:${Versions.awaitility}")
		}

		fun neo4j(scope: Scope) = scope.add(
			"org.neo4j:neo4j-ogm-core:${Versions.neo4jOgm}",
			"org.neo4j:neo4j-ogm-bolt-driver:${Versions.neo4jOgm}"
		)

		object Fs {
			fun client(scope: Scope) = scope.add(
				"io.komune.fs:fs-file-client:${Versions.fs}",
				"io.komune.fs:fs-spring-utils:${Versions.fs}",
				"io.ktor:ktor-utils:${Versions.ktor}"
			)
		}

		object Spring {
			fun dataCommons(scope: Scope) = FixersDependencies.Jvm.Spring.dataCommons(scope)
			fun tx(scope: Scope) = FixersDependencies.Jvm.Test.junit(scope).also {
				scope.add("org.springframework:spring-tx:${FixersVersions.Spring.framework}")
			}
			fun autoConfigure(scope: Scope, ksp: Scope)
					= FixersDependencies.Jvm.Spring.autoConfigure(scope, ksp)
		}

		object Test {
			fun dataFaker(scope: Scope) = scope.add(
				"net.datafaker:datafaker:${Versions.datafaker}",
			)
		}

	}
	object Mpp {
		fun f2(scope: Scope) = scope.add(
			"io.komune.f2:f2-dsl-cqrs:${Framework.fixers}",
			"io.komune.f2:f2-dsl-function:${Framework.fixers}"
		)

		fun f2Client(scope: Scope) = scope.add(
			"io.komune.f2:f2-client-ktor:${Framework.fixers}",
			"io.ktor:ktor-client-auth:${Versions.ktor}",
			"io.ktor:ktor-client-logging:${Versions.ktor}",
		)

		fun fs(scope: Scope) = scope.add(
			"io.komune.fs:fs-file-domain:${Versions.fs}"
		)

		fun s2(scope: Scope) = scope.add(
			"io.komune.s2:s2-automate-core:${Versions.s2}",
			"io.komune.s2:s2-automate-dsl:${Versions.s2}"
		)
	}
}

object Modules {
	object api {
		const val commons = ":api-commons"
		const val config = ":api-config"
		const val gateway = ":api-gateway"
	}

	object cccev {

		object dsl {
			const val client = ":cccev-dsl:cccev-dsl-client"
			const val model = ":cccev-dsl:cccev-dsl-model"
		}
		object f2 {
			const val commons = ":cccev-f2:cccev-f2-commons"
			object certification {
				const val api = ":cccev-f2:certification-f2:cccev-certification-f2-api"
				const val client = ":cccev-f2:certification-f2:cccev-certification-f2-client"
				const val domain = ":cccev-f2:certification-f2:cccev-certification-f2-domain"
			}
			object concept {
				const val api = ":cccev-f2:concept-f2:cccev-concept-f2-api"
				const val client = ":cccev-f2:concept-f2:cccev-concept-f2-client"
				const val domain = ":cccev-f2:concept-f2:cccev-concept-f2-domain"
			}
			object evidence {
				const val api = ":cccev-f2:evidence-f2:cccev-evidence-f2-api"
				const val client = ":cccev-f2:evidence-f2:cccev-evidence-f2-client"
				const val domain = ":cccev-f2:evidence-f2:cccev-evidence-f2-domain"
			}
			object evidenceType {
				const val api = ":cccev-f2:evidence-type-f2:cccev-evidence-type-f2-api"
				const val client = ":cccev-f2:evidence-type-f2:cccev-evidence-type-f2-client"
				const val domain = ":cccev-f2:evidence-type-f2:cccev-evidence-type-f2-domain"
			}
			object framework {
				const val api = ":cccev-f2:framework-f2:cccev-framework-f2-api"
				const val client = ":cccev-f2:framework-f2:cccev-framework-f2-client"
				const val domain = ":cccev-f2:framework-f2:cccev-framework-f2-domain"
			}
			object requirement {
				const val api = ":cccev-f2:requirement-f2:cccev-requirement-f2-api"
				const val client = ":cccev-f2:requirement-f2:cccev-requirement-f2-client"
				const val domain = ":cccev-f2:requirement-f2:cccev-requirement-f2-domain"
			}
			object unit {
				const val api = ":cccev-f2:unit-f2:cccev-unit-f2-api"
				const val client = ":cccev-f2:unit-f2:cccev-unit-f2-client"
				const val domain = ":cccev-f2:unit-f2:cccev-unit-f2-domain"
			}
		}
		object infra {
			const val fs = ":cccev-infra:fs"
			const val neo4j = ":cccev-infra:neo4j"
		}
		object projection {
			const val api = ":cccev-projection:cccev-projection-api"
			const val domain = ":cccev-projection:cccev-projection-domain"
		}
		object s2 {
			object concept {
				const val api = ":cccev-s2:concept:cccev-concept-api"
				const val domain = ":cccev-s2:concept:cccev-concept-domain"
			}
			object evidence {
				const val api = ":cccev-s2:evidence:cccev-evidence-api"
				const val domain = ":cccev-s2:evidence:cccev-evidence-domain"
			}
			object evidenceType {
				const val api = ":cccev-s2:evidence-type:cccev-evidence-type-api"
				const val domain = ":cccev-s2:evidence-type:cccev-evidence-type-domain"
			}
			object framework {
				const val api = ":cccev-s2:framework:cccev-framework-api"
				const val domain = ":cccev-s2:framework:cccev-framework-domain"
			}
			object requirement {
				const val api = ":cccev-s2:requirement:cccev-requirement-api"
				const val domain = ":cccev-s2:requirement:cccev-requirement-domain"
			}
			object unit {
				const val api = ":cccev-s2:unit:cccev-unit-api"
				const val domain = ":cccev-s2:unit:cccev-unit-domain"
			}
		}
		const val core = ":cccev-core"
		const val test = ":cccev-test"

		private const val BASE = ":im-commons:im-commons"
		const val api = "$BASE-api"
		const val auth = "$BASE-auth"
		const val domain = "$BASE-domain"
	}


}
