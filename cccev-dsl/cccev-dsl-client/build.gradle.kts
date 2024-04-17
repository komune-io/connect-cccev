plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
}

dependencies {
    commonMainApi(project(Modules.cccev.f2.concept.client))
    commonMainApi(project(Modules.cccev.f2.evidenceType.client))
    commonMainApi(project(Modules.cccev.f2.framework.client))
    commonMainApi(project(Modules.cccev.f2.certification.client))
    commonMainApi(project(Modules.cccev.f2.requirement.client))
    commonMainApi(project(Modules.cccev.f2.unit.client))
    commonMainApi(project(Modules.cccev.dsl.model))

    jvmMainApi(project(Modules.cccev.f2.commons))
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
