FROM openjdk:17-jdk-alpine

WORKDIR /build

COPY target/data-base-app-0.0.1-SNAPSHOT.jar /build/data-base-app.jar

ENTRYPOINT ["java", "-jar", "data-base-app.jar"]