FROM openjdk:17-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY /app/static /resources
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]
