plugins {
    `java-library`
    id("org.openapi.generator") version "7.13.0"
}

group = "io.github.orionlibs"
version = "0.0.1"

dependencies {
  implementation("com.squareup.okhttp3:okhttp:5.1.0")
}

openApiGenerate {
  inputSpec.set("${projectDir}/../user-service/build/openapi/openapi.json")
  generatorName.set("java")
  library.set("okhttp-gson")
  outputDir.set("$buildDir/generated")  
  apiPackage.set("io.github.orionlibs.sdk.user.api")
  modelPackage.set("io.github.orionlibs.sdk.user.model")
  invokerPackage.set("io.github.orionlibs.sdk.user.invoker")
  configOptions.set(
    mapOf(
      "dateLibrary" to "java8",
      "useTags" to "true"
    )
  )
}

sourceSets {
  main {
    java {
      srcDir("$buildDir/generated/src/main/java")
    }
  }
}

tasks.named("compileKotlin") {
  dependsOn("openApiGenerate")
}
