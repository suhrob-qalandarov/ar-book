# Use JDK 21 base image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the built JAR file and application.properties
COPY target/*.jar app.jar
COPY src/main/resources/application.properties application.properties

# Expose the default Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]