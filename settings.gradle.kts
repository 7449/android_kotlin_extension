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
include(
    ":sample",
)
include(
    ":extension",
    ":extension-bom",
    ":compose",
    ":compose-material",
    ":compose-material3",
)
