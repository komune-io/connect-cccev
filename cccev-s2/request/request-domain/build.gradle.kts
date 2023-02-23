plugins {
	id("city.smartb.fixers.gradle.kotlin.mpp")
	id("city.smartb.fixers.gradle.publish")
	kotlin("plugin.serialization")
}

dependencies {
	commonMainApi(project(":cccev-dsl:dsl-model"))
	commonMainApi("city.smartb.s2:s2-automate-dsl:${Versions.s2}")
}
