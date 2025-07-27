import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    application
    jacoco
    `base`
    `maven-publish`
    id("io.spring.dependency-management") version "1.1.7"
    id("org.springframework.boot") version "3.5.3"
    id("com.github.ben-manes.versions") version "0.52.0"
    id("org.sonarqube") version "6.2.0.5505"
    id("com.vanniktech.dependency.graph.generator") version "0.7.0"
    id("org.springdoc.openapi-gradle-plugin") version "1.9.0"
}

openApi {
  apiDocsUrl.set("http://localhost:8080/api/docs")
  outputDir.set(layout.buildDirectory.dir("openapi"))
  outputFileName.set("openapi.json")
  waitTimeInSeconds.set(30)
  customBootRun {
    systemProperties.put("spring.profiles.active", "test")
  }
}

tasks.named("build") {
  dependsOn("generateOpenApiDocs")
}

group = "io.github.orionlibs"
version = "0.0.1"

repositories {
    mavenLocal()
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    mainClass.set("io.github.orionlibs.user.Application")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            groupId = "io.github.orionlibs"
            artifactId = "${project.name}"
            version = "0.0.1"
        }
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(listOf("-Xlint:unchecked", "-Werror"))
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
    }
}

tasks.named<JacocoReport>("jacocoTestReport") {
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true)
    }
}

tasks.named("sonarqube") {
    dependsOn("jacocoTestReport")
}
sonarqube {
    properties {
        property("sonar.projectName", "UNS :: ${project.name}")
        property("sonar.projectKey",  "io.github.orionlibs:${project.name}")
    }
}

// Dependency updates
tasks.named<DependencyUpdatesTask>("dependencyUpdates") {
    rejectVersionIf {
        val v = candidate.version
        val stable = listOf("RELEASE", "FINAL", "GA").any { kv -> v.uppercase().contains(kv) }
        val pattern = Regex("^[0-9,.v-]+$")
        !stable && !pattern.matches(v) && pattern.matches(currentVersion)
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-observation")
    implementation("io.micrometer:micrometer-tracing-bridge-brave")
    implementation("io.micrometer:micrometer-core")
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-json")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.mysql:mysql-connector-j:9.3.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.9")
    implementation("io.rest-assured:rest-assured")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    runtimeOnly("com.h2database:h2")
    
    implementation("io.github.orionlibs:core:0.0.1")

    testImplementation(platform("org.junit:junit-bom:5.13.3"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}


// BootJar customization
tasks.named<BootJar>("bootJar") {
    archiveFileName.set("app.jar")
    manifest {
        attributes("Implementation-Version" to project.version.toString())
    }
}


tasks.register<Copy>("exportOpenApi") {
    dependsOn("bootJar")
    from(layout.buildDirectory.file("resources/main/static/v3/api-docs"))
    into(layout.buildDirectory.dir("openapi"))
    rename("v3/api-docs", "openapi.json")
}



tasks.named("exportOpenApi") {
  dependsOn("compileJava")
}
