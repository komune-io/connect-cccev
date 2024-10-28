plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.serialization")
}

dependencies {
    Dependencies.Mpp.fs(::commonMainApi)
    commonMainApi(project(Modules.cccev.dsl.model))
}
