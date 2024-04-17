plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.spring")
    kotlin("plugin.serialization")
}

dependencies {
    commonMainApi(project(Modules.cccev.core))
    commonMainApi(project(Modules.cccev.f2.concept.domain))
    commonMainApi(project(Modules.cccev.f2.evidence.domain))
    commonMainApi(project(Modules.cccev.f2.requirement.domain))

    commonMainApi("io.ktor:ktor-http:2.2.4")

    Dependencies.Mpp.fs(::commonMainApi)
}
