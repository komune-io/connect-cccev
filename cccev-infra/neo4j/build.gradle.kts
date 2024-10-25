plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
	kotlin("plugin.spring")
	kotlin("plugin.serialization")
	kotlin("kapt")
}

dependencies {
	implementation(project(Modules.api.commons))

	Dependencies.Jvm.neo4j(::api)
	Dependencies.Jvm.Spring.autoConfigure(::implementation, ::kapt)
	implementation("io.komune.f2:f2-spring-boot-exception-http:${Framework.fixers}")
}
