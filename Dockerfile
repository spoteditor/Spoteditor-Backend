FROM openjdk:17-jdk

COPY spoteditor.jar spoteditor.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/spoteditor.jar"]
