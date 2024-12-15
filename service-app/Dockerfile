FROM openjdk:17-jdk-alpine

WORKDIR /build

COPY target/service-app-0.0.1-SNAPSHOT.jar /build/service-app.jar

ENTRYPOINT ["java", "-jar", "service-app.jar"]