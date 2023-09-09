FROM openjdk:17.0.1-jdk-slim
ADD /target/discord.jar api.jar
ENTRYPOINT ["java", "-jar", "api.jar"]
