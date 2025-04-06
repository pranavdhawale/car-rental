FROM openjdk:23-jdk-slim
WORKDIR /app
COPY target/payment_service-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/app.jar"]