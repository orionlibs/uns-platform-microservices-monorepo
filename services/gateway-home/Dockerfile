FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app
COPY gradlew .
COPY gradle ./gradle
RUN chmod +x gradlew
COPY settings.gradle.kts build.gradle.kts ./
COPY libs libs
COPY services/gateway-home ./services/gateway-home
RUN ./gradlew :services:gateway-home:bootJar --no-daemon
FROM eclipse-temurin:21-jre-jammy
EXPOSE 80
COPY --from=build /app/services/gateway-home/build/libs/app.jar /app.jar
ENTRYPOINT java -jar "/app.jar"
