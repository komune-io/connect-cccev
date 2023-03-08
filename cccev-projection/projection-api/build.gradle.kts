plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
	kotlin("plugin.spring")
}

dependencies {
	api(project(":cccev-projection:projection-domain"))

	api("org.springframework.boot:spring-boot-starter-data-neo4j:${Versions.springBoot}")

	testImplementation("org.springframework.boot:spring-boot-starter-test:${Versions.springBoot}")
}
