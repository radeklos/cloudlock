plugins {
    id("org.jetbrains.kotlin.plugin.spring") version "1.7.21"
}


dependencies {
    implementation(project(":cloudlock-core"))

    implementation(libs.spring.boot.starter)

    implementation(libs.kotlin.logging.jvm)
    testImplementation(libs.bundles.kotest)
}

