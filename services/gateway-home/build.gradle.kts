import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    application
    jacoco
    id("io.spring.dependency-management") version "1.1.7"
    id("org.springframework.boot") version "3.5.3"
    id("com.github.ben-manes.versions") version "0.52.0"
    id("org.sonarqube") version "6.2.0.5505"
}

group = "io.github.orionlibs"
version = "0.0.1"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    mainClass.set("io.github.orionlibs.${project.name}.Application")
}

// -- custom test source sets ------------------------------------------------

val allTests by sourceSets.creating {
    java.srcDir("src/test/java")
    resources.srcDir("src/test/resources")
    compileClasspath += sourceSets["main"].output
    runtimeClasspath += sourceSets["main"].output
}

configurations {
    named("allTestsImplementation") { extendsFrom(configurations.testImplementation.get()) }
    named("allTestsRuntimeOnly")   { extendsFrom(configurations.runtimeOnly.get()) }
}

// -- compile & test settings ------------------------------------------------

tasks.withType<JavaCompile> {
    options.compilerArgs.addAll(listOf("-Xlint:unchecked", "-Werror"))
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
    }
}

// standard `test`
tasks.named<Test>("test") {
    failFast = true
}

tasks.register<Test>("allTests") {
    description = "Runs all tests"
    group       = "verification"
    testClassesDirs = allTests.output.classesDirs
    classpath       = allTests.runtimeClasspath
    shouldRunAfter(tasks.named("test"))
}

// make `check` depend on them
tasks.named("check") {
    dependsOn("allTests")
}

// Jacoco report (uses default executionData)
tasks.named<JacocoReport>("jacocoTestReport") {
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true)
    }
}

// Sonar
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
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-json")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.mysql:mysql-connector-j:9.3.0")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.9")
    implementation("io.rest-assured:rest-assured")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    
    // implementation(project(":libs:calendar"))

    testImplementation(platform("org.junit:junit-bom:5.13.3"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.platform:junit-platform-launcher")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.h2database:h2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

// BootJar customization
tasks.named<BootJar>("bootJar") {
    archiveFileName.set("app.jar")
    manifest {
        attributes("Implementation-Version" to project.version.toString())
    }
}
