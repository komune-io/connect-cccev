import city.smartb.gradle.dependencies.FixersDependencies
import city.smartb.gradle.dependencies.FixersPluginVersions
import city.smartb.gradle.dependencies.FixersVersions
import city.smartb.gradle.dependencies.Scope
import city.smartb.gradle.dependencies.add

object PluginVersions {
//	const val kotlin = FixersPluginVersions.kotlin
	const val kotlin = "1.6.20-M1"
	const val springBoot = FixersPluginVersions.springBoot
	const val fixers = "experimental-SNAPSHOT"
	const val d2 = "0.3.1"
}

object Versions {
	const val s2 = "experimental-x-SNAPSHOT"
	const val f2 = PluginVersions.fixers

	const val cucumber = FixersVersions.Test.cucumber
	const val awaitility = "4.1.1"
}

object Repo {
	val snapshot: List<String> = listOf(
		// For fixers
		"https://oss.sonatype.org/content/repositories/snapshots",
		//For pdfbox
		"https://jitpack.io"
	)
}

object Dependencies {
	object Jvm {
		fun f2(scope: Scope) = scope.add(
			"city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}"
		)

		fun s2Mongo(scope: Scope) = scope.add(
			"city.smartb.s2:s2-spring-boot-starter-automate:${Versions.s2}",
			"city.smartb.s2:s2-spring-boot-starter-automate-data:${Versions.s2}",
			"city.smartb.f2:f2-spring-data-mongodb:${Versions.f2}",
			"city.smartb.s2:s2-spring-boot-starter-utils-logger:${Versions.s2}"
		)

		fun coroutines(scope: Scope) = FixersDependencies.Jvm.Kotlin.coroutines(scope)
		fun cucumber(scope: Scope) = FixersDependencies.Jvm.Test.cucumber(scope).also {
			scope.add("io.cucumber:cucumber-spring:${Versions.cucumber}")
		}
		fun junit(scope: Scope) = FixersDependencies.Jvm.Test.junit(scope).also {
			scope.add("org.awaitility:awaitility:${Versions.awaitility}")
		}
	}
	object Mpp {
		fun f2(scope: Scope) = scope.add(
			"city.smartb.f2:f2-dsl-cqrs:${Versions.f2}",
			"city.smartb.f2:f2-dsl-function:${Versions.f2}"
		)

		fun s2(scope: Scope) = scope.add(
			"city.smartb.s2:s2-automate-core:${Versions.s2}",
			"city.smartb.s2:s2-automate-dsl:${Versions.s2}"
		)
	}
}
