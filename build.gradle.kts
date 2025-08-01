plugins {
    id("java")
    id("org.springframework.boot") version "3.5.4" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
    id("com.vanniktech.dependency.graph.generator") version "0.7.0"
    // id("com.bmuschko.docker-spring-boot-application") version "9.4.0" apply false
}

allprojects {
    group = "io.github.orionlibs"
    version = "0.0.1"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "base")

    java {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}


tasks.register("prepareApiSdk") {
  group = "build setup"
  description = "Generate OpenAPI spec and SDK"
  dependsOn(
    ":services:user-service:generateOpenApiDocs",
    ":services:user-sdk:openApiGenerate"
  )
}

tasks.named("build") {
  dependsOn("prepareApiSdk")
}
