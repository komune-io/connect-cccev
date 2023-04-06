import city.smartb.gradle.dependencies.FixersDependencies
import city.smartb.gradle.dependencies.FixersPluginVersions
import city.smartb.gradle.dependencies.FixersVersions
import city.smartb.gradle.dependencies.Scope
import city.smartb.gradle.dependencies.add

object PluginVersions {
	const val kotlin = FixersPluginVersions.kotlin
	const val springBoot = FixersPluginVersions.springBoot
	val fixers = FixersPluginVersions.fixers
	val d2 = PluginVersions.fixers
}

object Versions {
	val s2 = PluginVersions.fixers
	val f2 = PluginVersions.fixers
	val i2 = PluginVersions.fixers
	val fs = PluginVersions.fixers
	val im = PluginVersions.fixers
	val springBoot = PluginVersions.springBoot

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

		fun s2Bdd(scope: Scope) = scope.add(
			"city.smartb.s2:s2-test-bdd:${Versions.s2}",
			"org.springframework.boot:spring-boot-starter-test:${PluginVersions.springBoot}"
		).also(::cucumber)
			.also(::junit)

		fun f2(scope: Scope) = scope.add(
			"city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}"
		)

		fun fs(scope: Scope) = scope.add(
			"city.smartb.fs:file-client:${Versions.fs}"
		)

		fun s2EventSouringBc(scope: Scope) = scope.add(
			"city.smartb.s2:s2-spring-boot-starter-sourcing-ssm:${Versions.s2}",
			"city.smartb.s2:s2-spring-boot-starter-utils-logger:${Versions.s2}"
		)

		fun s2Logger(scope: Scope) = scope.add(
			"city.smartb.s2:s2-spring-boot-starter-utils-logger:${Versions.s2}"
		)

		fun jackson(scope: Scope) = FixersDependencies.Jvm.Json.jackson(scope)
		fun coroutines(scope: Scope) = FixersDependencies.Jvm.Kotlin.coroutines(scope)
		fun cucumber(scope: Scope) = FixersDependencies.Jvm.Test.cucumber(scope).also {
			scope.add("io.cucumber:cucumber-spring:${Versions.cucumber}")
		}
		fun junit(scope: Scope) = FixersDependencies.Jvm.Test.junit(scope).also {
			scope.add("org.awaitility:awaitility:${Versions.awaitility}")
		}

		object Spring {
			fun dataCommons(scope: Scope) = FixersDependencies.Jvm.Spring.dataCommons(scope)
			fun tx(scope: Scope) = FixersDependencies.Jvm.Test.junit(scope).also {
				scope.add("org.springframework:spring-tx:${FixersVersions.Spring.framework}")
			}
		}

	}
	object Mpp {
		fun f2(scope: Scope) = scope.add(
			"city.smartb.f2:f2-dsl-cqrs:${Versions.f2}",
			"city.smartb.f2:f2-dsl-function:${Versions.f2}"
		)

		fun fs(scope: Scope) = scope.add(
			"city.smartb.fs:file-domain:${Versions.fs}"
		)

		fun im(scope: Scope) = scope.add(
			"city.smartb.im:im-commons-domain:${Versions.im}"
		)

		fun s2(scope: Scope) = scope.add(
			"city.smartb.s2:s2-automate-core:${Versions.s2}",
			"city.smartb.s2:s2-automate-dsl:${Versions.s2}"
		)
	}
}
