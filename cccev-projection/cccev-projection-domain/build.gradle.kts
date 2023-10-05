plugins {
	id("city.smartb.fixers.gradle.kotlin.mpp")
	id("city.smartb.fixers.gradle.publish")
	kotlin("plugin.serialization")
}

dependencies {
	commonMainApi(project(Modules.cccev.s2.concept.domain))
	commonMainApi(project(Modules.cccev.s2.evidenceType.domain))
	commonMainApi(project(Modules.cccev.s2.framework.domain))
	commonMainApi(project(Modules.cccev.s2.certification.domain))
	commonMainApi(project(Modules.cccev.s2.requirement.domain))
	commonMainApi(project(Modules.cccev.s2.unit.domain))
}
