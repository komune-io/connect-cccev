plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
}

dependencies {
    commonMainApi(project(Modules.cccev.client))
    commonMainApi(project(Modules.cccev.dsl.model))

    jvmMainApi(project(Modules.cccev.f2))
//    commonMainApi(project(Modules.cccev.f2.commons))
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
