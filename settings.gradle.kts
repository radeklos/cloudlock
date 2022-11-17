
rootProject.name = "cloud-lock"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("libs.versions.toml"))
        }
    }
}

include(
    ":cloudlock-core",
    ":cloudlock-spring",

    ":tests:spring-boot",
)