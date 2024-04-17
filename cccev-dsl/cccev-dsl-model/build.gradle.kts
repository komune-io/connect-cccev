plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.serialization")
}

dependencies {
    Dependencies.Jvm.junit(::jvmTestImplementation)
    Dependencies.Mpp.fs(::commonMainApi)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
