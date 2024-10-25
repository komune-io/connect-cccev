plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
}

dependencies {
    Dependencies.Jvm.coroutines(::jvmMainApi)
    Dependencies.Jvm.f2(::jvmMainApi)

    Dependencies.Jvm.Spring.tx(::jvmMainApi)
}
