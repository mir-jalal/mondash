FROM eclipse-temurin:21-jre-alpine

WORKDIR /opt/mondash

COPY build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
