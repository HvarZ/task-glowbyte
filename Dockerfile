FROM eclipse-temurin:17-jdk-alpine

COPY target/*.jar .

ENTRYPOINT ["java","-jar","practical-task-0.0.1-SNAPSHOT.jar"]