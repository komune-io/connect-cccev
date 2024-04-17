plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.cccev.f2.certification.domain))
    api(project(Modules.cccev.f2.concept.domain))
    api(project(Modules.cccev.f2.evidence.domain))
    api(project(Modules.cccev.f2.evidenceType.domain))
    api(project(Modules.cccev.f2.framework.domain))
    api(project(Modules.cccev.f2.requirement.domain))
    api(project(Modules.cccev.f2.unit.domain))
}
