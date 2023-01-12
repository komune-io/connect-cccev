plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("city.smartb.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
}

dependencies {
    api("city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}")
    api("city.smartb.f2:f2-spring-boot-openapi:${Versions.f2}")
    api("city.smartb.i2:i2-spring-boot-starter-auth-keycloak:${Versions.i2}")

//    implementation(project(":cccev-api"))

    implementation(project(":cccev-f2"))
    implementation(project(":cccev-f2:concept-api:concept-api-app"))
    implementation(project(":cccev-f2:evidence-api:evidence-api-app"))
    implementation(project(":cccev-f2:request-api:request-api-app"))
    implementation(project(":cccev-f2:requirement-api:requirement-api-app"))
    implementation(project(":cccev-s2:concept:concept-app"))
    implementation(project(":cccev-s2:evidence:evidence-app"))
    implementation(project(":cccev-s2:request:request-tasks"))
    implementation(project(":cccev-s2:requirement:requirement-app"))
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootBuildImage> {
    imageName.set("smartbcity/cccev-gateway:${this.project.version}")
}
