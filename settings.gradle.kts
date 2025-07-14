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
    maven { url = uri("https://jitpack.io") }
  }
}


rootProject.name = "uns-platform-microservices-monorepo"
// include("libs:core")
include("services:documents")
