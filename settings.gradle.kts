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
include("services:user-service", "services:user-sdk")
include("services:documents")
