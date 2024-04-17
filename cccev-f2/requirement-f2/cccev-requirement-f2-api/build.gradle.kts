plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.cccev.f2.requirement.domain))

    api(project(Modules.cccev.f2.commons))
    implementation(project(Modules.cccev.f2.concept.api))
    implementation(project(Modules.cccev.f2.evidenceType.api))

    implementation(project(Modules.cccev.s2.requirement.api))
}
