plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.cccev.f2.unit.domain))

    api(project(Modules.cccev.f2.commons))
    implementation(project(Modules.cccev.s2.unit.api))
}
