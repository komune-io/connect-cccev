plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
	kotlin("plugin.spring")
}

dependencies {
	implementation(project(Modules.api.config))
	implementation(project(Modules.cccev.dsl.model))
	implementation(project(Modules.cccev.core))
	implementation(project(Modules.cccev.f2.api))

	Dependencies.Jvm.neo4j(::api)
	Dependencies.Jvm.f2Http(::api)
	Dependencies.Jvm.f2Auth(::api)
	Dependencies.Jvm.s2Bdd(::api)
	Dependencies.Jvm.Test.dataFaker(::implementation)

	implementation("io.github.origin-energy:java-snapshot-testing-core:${Versions.javaSnapshotTesting}")
	implementation("io.github.origin-energy:java-snapshot-testing-plugin-jackson:${Versions.javaSnapshotTesting}")
	implementation("org.skyscreamer:jsonassert:${Versions.jsonAssert}")
}
