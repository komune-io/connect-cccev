plugins {
	kotlin("plugin.jpa") version PluginVersions.kotlin apply false
	kotlin("plugin.spring") version PluginVersions.kotlin apply false
	kotlin("plugin.serialization") version PluginVersions.kotlin apply false
	kotlin("kapt") version PluginVersions.kotlin apply false

	id("org.springframework.boot") version PluginVersions.springBoot apply false

	id("city.smartb.fixers.gradle.config") version PluginVersions.fixers
	id("city.smartb.fixers.gradle.d2") version PluginVersions.d2
	id("city.smartb.fixers.gradle.publish") version PluginVersions.fixers apply false
	id("city.smartb.fixers.gradle.sonar") version PluginVersions.fixers
}

allprojects {
	group = "city.smartb.cccev"
	version = System.getenv("VERSION") ?: "experimental-SNAPSHOT"
	repositories {
		mavenLocal()
		mavenCentral()
		Repo.snapshot.forEach {
			maven { url = uri(it) }
		}
	}
}

fixers {
	bundle {
		id = "cccev"
		name = "CCCEV"
		description = "Kotlin implementation of Core Criterion and Core Evidence Vocabulary"
		url = "https://gitlab.smartb.city/fixers/cccev"
	}
	d2 {
		outputDirectory = file("storybook/d2/")
	}
	kt2Ts {
		outputDirectory = "web/kotlin"
	}
}
