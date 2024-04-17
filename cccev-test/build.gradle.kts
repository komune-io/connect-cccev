plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
	kotlin("plugin.spring")
}

dependencies {
	implementation(project(Modules.cccev.dsl.model))

	implementation(project(Modules.api.config))

	implementation(project(Modules.cccev.f2.concept.api))
	implementation(project(Modules.cccev.f2.evidence.api))
	implementation(project(Modules.cccev.f2.evidenceType.api))
	implementation(project(Modules.cccev.f2.framework.api))
	implementation(project(Modules.cccev.f2.certification.api))
	implementation(project(Modules.cccev.f2.requirement.api))
	implementation(project(Modules.cccev.f2.unit.api))

	// TODO SHOULD BE DELETED
	implementation(project(Modules.cccev.s2.concept.api))
	implementation(project(Modules.cccev.s2.evidenceType.api))
	implementation(project(Modules.cccev.s2.framework.api))
	implementation(project(Modules.cccev.s2.requirement.api))
	implementation(project(Modules.cccev.s2.unit.api))

	implementation(project(Modules.cccev.core))

	Dependencies.Jvm.f2Http(::api)
	Dependencies.Jvm.s2Bdd(::api)
	Dependencies.Jvm.Test.dataFaker(::implementation)

	implementation("io.github.origin-energy:java-snapshot-testing-core:${Versions.javaSnapshotTesting}")
	implementation("io.github.origin-energy:java-snapshot-testing-plugin-jackson:${Versions.javaSnapshotTesting}")
	implementation("org.skyscreamer:jsonassert:${Versions.jsonAssert}")
}
