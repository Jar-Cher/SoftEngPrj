## CI for master:
[![CI master](https://github.com/Jar-Cher/SoftEngPrj/actions/workflows/maven.yml/badge.svg?branch=master)](https://github.com/Jar-Cher/SoftEngPrj/actions/workflows/maven.yml)
## CI for Develop:
[![CI Develop](https://github.com/Jar-Cher/SoftEngPrj/actions/workflows/maven.yml/badge.svg?branch=Develop)](https://github.com/Jar-Cher/SoftEngPrj/actions/workflows/maven.yml)

# SoftEngPrj
Software engineering excercise

This service's purpuse to provide user some basic information about weather in the desired city.

Usage, via Docker:
1. Build docker image via ```docker build -t [image_name] https://github.com/Jar-Cher/SoftEngPrj.git```
2. Run ```docker run [image_name] java -jar TemperatureService.jar [city_name]```

Alternatively:
1. Download, unzip and build project
2. From the terminal run ```java -jar TemperatureService.jar [city_name]```


