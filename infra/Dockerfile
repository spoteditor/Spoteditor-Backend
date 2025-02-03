FROM openjdk:17-alpine
COPY ./build/libs/*SNAPSHOT.jar spoteditor.jar

EXPOSE 8080 8081

ENTRYPOINT ["java", "-jar", "-Dserver.port=${SERVER_PORT}", "-Dspring.profiles.active=prod", "spoteditor.jar"]
