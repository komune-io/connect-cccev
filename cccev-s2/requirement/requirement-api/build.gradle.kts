plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
	kotlin("plugin.spring")
}

dependencies {
	implementation(project(":api-commons"))
	api(project(":cccev-s2:requirement:requirement-domain"))
}
