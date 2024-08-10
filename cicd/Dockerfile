FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}","-jar","/app.jar"]
