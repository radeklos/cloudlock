plugins {
    id("org.jetbrains.kotlin.plugin.spring") version "1.8.0"
}


dependencies {
    implementation(libs.spring.boot.starter)
    implementation(libs.org.jooq.jooq)

    implementation(libs.hikari)
    implementation(libs.jakarta.xml.bind.api)
    implementation(libs.jaxb.api)

    implementation(libs.spring.boot.starter.data.jdbc)
    implementation(libs.spring.boot.starter.web)

    implementation(libs.kotlin.logging.jvm)
    testImplementation(libs.h2)
    testImplementation(libs.postgresql)
    testImplementation(libs.mysql)
    testImplementation(libs.bundles.kotest)
    testImplementation(libs.bundles.spring.test)
}
