plugins {
    id("org.springframework.boot") version "2.7.6"
    id("org.jetbrains.kotlin.plugin.spring") version "1.7.21"
}

dependencies {
    implementation(project(":cloudlock-spring"))

    implementation(platform(libs.spring.boot.bom))
    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.kotlin.logging.jvm)

    implementation("org.slf4j:slf4j-api:2.0.0-alpha1")
    implementation("org.slf4j:slf4j-simple:2.0.0-alpha1")

    implementation(libs.postgresql)

    testImplementation(libs.bundles.spring.test)
}
