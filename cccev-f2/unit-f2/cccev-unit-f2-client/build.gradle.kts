plugins {
	id("io.komune.fixers.gradle.kotlin.mpp")
	id("io.komune.fixers.gradle.publish")
	kotlin("plugin.serialization")
}

dependencies {
	commonMainApi(project(Modules.cccev.f2.unit.domain))
	Dependencies.Mpp.f2Client(::commonMainApi)
}
