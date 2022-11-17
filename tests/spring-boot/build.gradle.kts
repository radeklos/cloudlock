plugins {
    id("org.springframework.boot") version "2.7.3"
    id("org.jetbrains.kotlin.plugin.spring") version "1.7.21"
}

dependencies {
    implementation(platform(libs.spring.boot.bom))
    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.kotlin.logging.jvm)

    testImplementation(libs.bundles.kotest)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.kotest.spring)
}
