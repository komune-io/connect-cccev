plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
}

subprojects {
    plugins.withType(JavaPlugin::class.java).whenPluginAdded {
        dependencies {
            val implementation by configurations
            implementation(project(Modules.api.commons))
            Dependencies.Jvm.f2 { implementation(it) }
        }
    }

    plugins.withType(org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper::class.java).whenPluginAdded {
        dependencies {
            val commonMainApi by configurations
            Dependencies.Mpp.f2 { commonMainApi(it) }
        }
    }
}
