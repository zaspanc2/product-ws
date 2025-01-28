# Docker file for building a docker image.
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/product-ws-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]