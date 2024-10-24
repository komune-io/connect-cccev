plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.serialization")
}

dependencies {
    commonMainApi(project(Modules.cccev.core))
    commonMainApi(project(Modules.cccev.f2.concept.domain))
    commonMainApi(project(Modules.cccev.f2.evidence.domain))
    commonMainApi(project(Modules.cccev.f2.requirement.domain))

    Dependencies.Mpp.fs(::commonMainApi)
}
