FROM openjdk:17-jdk

COPY build/libs/*SNAPSHOT.jar /spoteditor.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/spoteditor.jar"]