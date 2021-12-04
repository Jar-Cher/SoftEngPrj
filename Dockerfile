FROM openjdk:8
COPY ./out/artifacts/TemperatureService_jar /tmp
WORKDIR /tmp