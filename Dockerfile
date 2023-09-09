FROM eclipse-temurin:17-jdk-alpine as build
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN ./mvnw clean package

FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /target/discord.jar api.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "api.jar"]

