FROM openjdk:17-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources /app/static 
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]
