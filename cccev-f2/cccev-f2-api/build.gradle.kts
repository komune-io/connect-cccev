plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.api.commons))
    api(project(Modules.cccev.core))
    Dependencies.Mpp.f2(::api)

    implementation(project(Modules.cccev.infra.fs))
    Dependencies.Jvm.f2(::implementation)
}
