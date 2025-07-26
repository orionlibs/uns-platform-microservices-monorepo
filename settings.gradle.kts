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


rootProject.name = "uns-platform-microservices-monorepo"
include("libs:core")
include("services:user-service")
include("services:user-sdk")
include("services:documents")
