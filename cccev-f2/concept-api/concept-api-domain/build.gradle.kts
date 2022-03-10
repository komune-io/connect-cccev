plugins {
    id("city.smartb.fixers.gradle.kotlin.mpp")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    commonMainApi(project(":cccev-f2:evidence-api:evidence-api-domain"))
    commonMainApi(project(":cccev-s2:concept:concept-domain"))
    commonMainApi(project(":cccev-s2:request:request-domain"))
}
