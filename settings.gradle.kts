
rootProject.name = "cloud-lock"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("libs.versions.toml"))
        }
        create("test") {
            from(files("test.libs.versions.toml"))
        }
    }
}

include(
    ":core",
)