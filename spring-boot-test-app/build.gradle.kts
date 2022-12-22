plugins {
    id("org.springframework.boot") version "2.7.7"
    id("org.jetbrains.kotlin.plugin.spring") version "1.7.22"
}

dependencies {
    implementation(project(":cloudlock-spring"))

    implementation(platform(libs.spring.boot.bom))
    implementation(libs.spring.boot.starter)

    implementation(libs.kotlin.logging.jvm)
    implementation(libs.spring.boot.starter.web)
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.slf4j:slf4j-api:2.0.6")
    implementation("org.slf4j:slf4j-simple:2.0.6")

    implementation(libs.postgresql)

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
