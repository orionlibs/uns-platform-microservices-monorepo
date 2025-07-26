pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}


dependencyResolutionManagement {
    repositories {
        mavenLocal()
        mavenCentral()
    }
}

rootProject.name = "core"
include("src")
