plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.serialization")
}

dependencies {
    commonMainApi(project(Modules.cccev.f2.certification.domain))
    commonMainApi(project(Modules.cccev.f2.concept.domain))
    commonMainApi(project(Modules.cccev.f2.evidence.domain))
    commonMainApi(project(Modules.cccev.f2.evidenceType.domain))
    commonMainApi(project(Modules.cccev.f2.framework.domain))
    commonMainApi(project(Modules.cccev.f2.requirement.domain))
    commonMainApi(project(Modules.cccev.f2.unit.domain))
}
