plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
	kotlin("plugin.spring")
}

dependencies {
	Dependencies.Jvm.s2EventSouringBc(::implementation)
	api(project(":cccev-s2:requirement:requirement-domain"))
	api(project(":cccev-projection:projection-api"))
}
