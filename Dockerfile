FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/ar-book-0.0.1-SNAPSHOT.jar app.jar
COPY application.properties /app/application.properties
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]