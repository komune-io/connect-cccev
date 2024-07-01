plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.cccev.f2.certification.domain))

    api(project(Modules.cccev.core))
    implementation(project(Modules.cccev.f2.commons))
    implementation(project(Modules.cccev.f2.concept.api))
    implementation(project(Modules.cccev.f2.evidence.api))
    implementation(project(Modules.cccev.f2.requirement.api))


    implementation(project(Modules.cccev.infra.fs))
}
