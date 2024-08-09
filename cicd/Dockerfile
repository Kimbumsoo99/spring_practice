# Dockerfile
FROM openjdk:17-jdk-slim

WORKDIR /app

# Gradle wrapper와 소스 파일 복사
COPY gradlew /app/
COPY gradle /app/gradle
COPY build.gradle /app/
COPY settings.gradle /app/
COPY src /app/src

# 권한 부여 (필요한 경우)
RUN chmod +x gradlew

# Gradle 빌드 실행
RUN ./gradlew bootJar --no-daemon -x test

# 실행할 JAR 파일 복사
COPY build/libs/your-application.jar /app/

ENTRYPOINT ["java", "-jar", "/app/your-application.jar"]
