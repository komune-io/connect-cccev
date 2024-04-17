plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.spring")
    kotlin("plugin.serialization")
}

dependencies {
    commonMainApi(project(Modules.cccev.f2.evidence.domain))
    commonMainApi(project(Modules.cccev.f2.unit.domain))
    commonMainApi(project(Modules.cccev.s2.concept.domain))
}
