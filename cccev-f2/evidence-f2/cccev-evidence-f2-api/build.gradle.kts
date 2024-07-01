plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.cccev.f2.commons))
    api(project(Modules.cccev.f2.evidence.domain))
    implementation(project(Modules.cccev.s2.requirement.api))
}
