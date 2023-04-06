plugins {
    id("city.smartb.fixers.gradle.kotlin.mpp")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
    kotlin("plugin.serialization")
}

dependencies {
    commonMainApi(project(":cccev-f2:concept-f2:concept-f2-domain"))
    commonMainApi(project(":cccev-f2:evidence-f2:evidence-f2-domain"))
    commonMainApi(project(":cccev-s2:request:request-domain"))
    commonMainApi("io.ktor:ktor-http:2.2.4")

    Dependencies.Mpp.fs(::commonMainApi)
}
