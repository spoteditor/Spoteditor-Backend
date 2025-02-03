FROM openjdk:17-jdk
COPY ./build/libs/*SNAPSHOT.jar spoteditor.jar

RUN apt-get update && \
    apt-get install -y git

EXPOSE 8080 8081

ENTRYPOINT ["java", "-jar", "-Dserver.port=${SERVER_PORT}", "-Dspring.profiles.active=prod", "spoteditor.jar"]