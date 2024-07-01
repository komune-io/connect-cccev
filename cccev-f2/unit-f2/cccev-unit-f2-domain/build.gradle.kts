plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    kotlin("plugin.spring")
    kotlin("plugin.serialization")
}

dependencies {
    commonMainApi(project(Modules.cccev.s2.unit.domain))
}
