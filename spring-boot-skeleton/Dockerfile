FROM eclipse-temurin:21-jdk-jammy AS build
ARG serviceName
WORKDIR /app
COPY ../../gradlew ./
COPY ../../gradle ./gradle
COPY settings.gradle.kts build.gradle.kts ./
COPY libs libs
COPY services/${serviceName} services/${serviceName}
RUN ./gradlew :services:${serviceName}:bootJar --no-daemon

FROM eclipse-temurin:21-jre-jammy
ARG serviceName
COPY --from=build /app/services/${serviceName}/build/libs/app.jar /app.jar
ENTRYPOINT java -jar "/app.jar"
