pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
include(":sample", ":extension")
include(
    ":flexbox",
    ":http",
    ":compose",
    ":compose-material",
    ":compose-material3",
    ":component-ui",
)
