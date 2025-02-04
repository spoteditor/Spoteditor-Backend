# 빌드 스테이지
FROM gradle:7-jdk17 AS builder
WORKDIR /app
COPY .. .
RUN gradle build --no-daemon

# 실행 스테이지
FROM openjdk:17-alpine
COPY --from=builder /app/build/libs/*SNAPSHOT.jar spoteditor.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "spoteditor.jar"]