FROM maven:3.8.4-openjdk-11 AS build
COPY src /src
COPY pom.xml .
RUN mvn -f /pom.xml clean package
# RUN cat /src/main/resources/META-INF/MANIFEST.MF

FROM openjdk:11-jre-slim
COPY --from=build /target/mainModule-1.0-SNAPSHOT.jar TemperatureService.jar
# ENTRYPOINT ["java","-jar","TemperatureService.jar", "Moscow"]
