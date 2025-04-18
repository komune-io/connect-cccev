plugins {
	id("io.komune.fixers.gradle.kotlin.mpp")
	id("io.komune.fixers.gradle.publish")
	kotlin("plugin.serialization")
}

dependencies {
	commonMainApi(project(Modules.cccev.f2.domain))
	Dependencies.Mpp.f2Client(::commonMainApi)
	Dependencies.Jvm.jackson(::jvmMainApi)
}
