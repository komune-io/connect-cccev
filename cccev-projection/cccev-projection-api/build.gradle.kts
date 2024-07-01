plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
	kotlin("plugin.spring")
}

dependencies {
	api(project(Modules.cccev.projection.domain))

	implementation(project(Modules.api.commons))
	implementation(project(Modules.cccev.infra.neo4j))

	api("org.springframework.boot:spring-boot-starter-data-neo4j:${Versions.springBoot}")
	testImplementation("org.springframework.boot:spring-boot-starter-test:${Versions.springBoot}")
}
