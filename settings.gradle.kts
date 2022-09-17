include(":app")
include(":core")
rootProject.name = "BeerStop"
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}
enableFeaturePreview("VERSION_CATALOGS")

include(":efficient-feature")
include(":home")
include(":search")
include(":description")
