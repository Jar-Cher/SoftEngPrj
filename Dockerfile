FROM maven:3.8.4-openjdk-11 AS build
COPY src /src
COPY pom.xml .
RUN mvn -f /pom.xml clean package
# RUN cat /src/main/resources/META-INF/MANIFEST.MF
# RUN ls /target

FROM openjdk:11-jre-slim
# RUN ls /
COPY --from=build /target/WeatherWidget-1.0-SNAPSHOT.jar TemperatureService.jar
# ENTRYPOINT ["java","-jar","TemperatureService.jar", "Moscow"]
