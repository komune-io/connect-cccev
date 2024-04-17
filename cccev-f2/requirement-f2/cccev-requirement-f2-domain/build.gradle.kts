plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.spring")
    kotlin("plugin.serialization")
}

dependencies {
    commonMainApi(project(Modules.cccev.f2.concept.domain))
    commonMainApi(project(Modules.cccev.f2.evidenceType.domain))
    commonMainApi(project(Modules.cccev.f2.framework.domain))
    commonMainApi(project(Modules.cccev.s2.requirement.domain))
}
