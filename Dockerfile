FROM eclipse-temurin:17-jdk

WORKDIR /app

# after WORKDIR /app
COPY .env ./

COPY target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]