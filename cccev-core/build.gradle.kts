plugins {
	id("io.komune.fixers.gradle.kotlin.mpp")
	id("io.komune.fixers.gradle.publish")
	kotlin("plugin.serialization")
	kotlin("plugin.spring")
}

dependencies {
	commonMainApi(project(Modules.cccev.s2.concept.domain))
	commonMainApi(project(Modules.cccev.s2.evidenceType.domain))
	commonMainApi(project(Modules.cccev.s2.requirement.domain))
	Dependencies.Mpp.fs(::commonMainApi)

	jvmMainApi(project(Modules.api.commons))
	jvmMainApi(project(Modules.cccev.dsl.model))
	jvmMainApi(project(Modules.cccev.projection.api))
	jvmMainApi(project(Modules.cccev.infra.neo4j))
}
