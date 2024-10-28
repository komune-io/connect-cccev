plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("io.komune.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
}

dependencies {
    Dependencies.Jvm.f2Http(::api)

    implementation(project(Modules.api.commons))
    implementation(project(Modules.api.config))

    implementation(project(Modules.cccev.core))
    implementation(project(Modules.cccev.f2.api))
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootBuildImage> {}
