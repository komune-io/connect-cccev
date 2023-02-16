subprojects {
	plugins.withType(JavaPlugin::class.java).whenPluginAdded {
		dependencies {
			val implementation by configurations
			implementation(project(":cccev-infra:mongodb"))
			Dependencies.Jvm.f2 { implementation(it) }
			Dependencies.Jvm.s2Mongo { implementation(it) }
		}
	}

	plugins.withType(org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper::class.java).whenPluginAdded {
		dependencies {
			val commonMainApi by configurations
			commonMainApi(project(":cccev-core:cccev-core-dsl"))
			commonMainApi(project(":api-commons"))
			Dependencies.Mpp.f2 { commonMainApi(it) }
			Dependencies.Mpp.s2 { commonMainApi(it) }
		}
	}
}
