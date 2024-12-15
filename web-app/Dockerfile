FROM openjdk:17-jdk-alpine

WORKDIR /build

COPY target/web-app-0.0.1-SNAPSHOT.jar /build/web-app.jar

ENTRYPOINT ["java", "-jar", "web-app.jar"]