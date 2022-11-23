plugins {
    id("org.jetbrains.kotlin.plugin.spring") version "1.7.21"
}


dependencies {
    implementation(libs.spring.boot.starter)
    implementation(libs.org.jooq.jooq)

    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("jakarta.xml.bind:jakarta.xml.bind-api:2.3.2")
    implementation("javax.xml.bind:jaxb-api:2.3.1")

    implementation(libs.spring.boot.starter.data.jdbc)
    implementation(libs.spring.boot.starter.web)

    implementation(libs.kotlin.logging.jvm)
    testImplementation(libs.h2)
    testImplementation(libs.postgresql)
    testImplementation(libs.bundles.kotest)
    testImplementation(libs.bundles.spring.test)
}
