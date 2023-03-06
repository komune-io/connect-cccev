plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
	kotlin("plugin.spring")
}

dependencies {
	implementation(project(":api-commons"))
	api(project(":cccev-s2:request:request-domain"))
	api(project(":cccev-dsl:dsl-model"))
}
