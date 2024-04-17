plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("io.komune.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
}

dependencies {
    Dependencies.Jvm.f2Http(::api)

    implementation(project(Modules.cccev.f2.concept.api))
    implementation(project(Modules.cccev.f2.evidence.api))
    implementation(project(Modules.cccev.f2.evidenceType.api))
    implementation(project(Modules.cccev.f2.framework.api))
    implementation(project(Modules.cccev.f2.certification.api))
    implementation(project(Modules.cccev.f2.requirement.api))
    implementation(project(Modules.cccev.f2.unit.api))

    //TODO s2 dependencies should be removed
    implementation(project(Modules.cccev.s2.concept.api))
    implementation(project(Modules.cccev.s2.evidenceType.api))
    implementation(project(Modules.cccev.s2.requirement.api))

    implementation(project(Modules.api.commons))
    implementation(project(Modules.api.config))

    implementation(project(Modules.cccev.core))
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootBuildImage> {}
