subprojects {
	plugins.withType(JavaPlugin::class.java).whenPluginAdded {
		dependencies {
			val implementation by configurations
			implementation(project(":cccev-infra:mongodb"))
			implementation(project(":api-commons"))
			Dependencies.Jvm.f2 { implementation(it) }
		}
	}

	plugins.withType(org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper::class.java).whenPluginAdded {
		dependencies {
			val commonMainApi by configurations
			commonMainApi(project(":cccev-dsl:dsl-model"))
			Dependencies.Mpp.f2 { commonMainApi(it) }
			Dependencies.Mpp.s2 { commonMainApi(it) }
		}
	}
}
