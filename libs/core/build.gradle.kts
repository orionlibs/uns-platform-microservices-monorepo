plugins {
    `java-library`
    `maven-publish`
    id("io.spring.dependency-management") version "1.1.7"
}

val springBootVersion = "3.5.3"

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion")
    }
}

repositories {
    mavenLocal()
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation(libs.guava)
    implementation("org.springframework.boot:spring-boot-starter-web")
    api("com.fasterxml.jackson.core:jackson-databind")
    api("com.fasterxml.jackson.core:jackson-annotations")
    api("com.fasterxml.jackson.core:jackson-core")

    testImplementation(platform("org.junit:junit-bom:5.13.3"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.assertj:assertj-core")
    //testImplementation(libs.junit.jupiter)

    //api(libs.commons.math3)
}


java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            groupId = "com.onelivery.libs"
            artifactId = "core"
            version = "0.0.1"
        }
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
