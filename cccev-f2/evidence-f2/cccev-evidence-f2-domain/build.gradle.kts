plugins {
    id("city.smartb.fixers.gradle.kotlin.mpp")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
    kotlin("plugin.serialization")
}

dependencies {
    commonMainApi(project(Modules.cccev.s2.certification.domain))
}
