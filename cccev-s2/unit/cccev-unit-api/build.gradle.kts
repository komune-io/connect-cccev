plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
	kotlin("plugin.spring")
}

dependencies {
	api(project(Modules.cccev.s2.unit.domain))
	api(project(Modules.cccev.projection.api))
	Dependencies.Jvm.s2EventSouringBc(::implementation)
}
