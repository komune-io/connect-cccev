plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.serialization")
    kotlin("plugin.spring")
}

dependencies {
    commonMainApi(project(Modules.api.commons))
    commonMainApi(project(Modules.cccev.core))
    Dependencies.Mpp.f2(::commonMainApi)

    jvmMainImplementation(project(Modules.cccev.infra.fs))
    Dependencies.Jvm.f2(::jvmMainImplementation)
}
