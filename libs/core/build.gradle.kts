plugins {
    `java-library`
    `maven-publish`
}

repositories {
    mavenLocal()
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    api(libs.commons.math3)

    implementation(libs.guava)
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
