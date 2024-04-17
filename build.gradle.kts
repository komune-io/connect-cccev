plugins {
	kotlin("plugin.jpa") version PluginVersions.kotlin apply false
	kotlin("plugin.spring") version PluginVersions.kotlin apply false
	kotlin("plugin.serialization") version PluginVersions.kotlin apply false
	kotlin("kapt") version PluginVersions.kotlin apply false

	id("org.springframework.boot") version PluginVersions.springBoot apply false

	id("io.komune.fixers.gradle.config") version PluginVersions.fixers
	id("io.komune.fixers.gradle.d2") version PluginVersions.d2
	id("io.komune.fixers.gradle.publish") version PluginVersions.fixers apply false
//	id("io.komune.fixers.gradle.check") version PluginVersions.fixers
}

allprojects {
	group = "io.komune.cccev"
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
		outputDirectory = file("storybook/stories/d2/")
	}
	kt2Ts {
		outputDirectory = "web/kotlin"
	}
}
