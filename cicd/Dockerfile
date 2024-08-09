# Base image
FROM bellsoft/liberica-openjdk-alpine:17

# Maintainer
LABEL maintainer="show7441@gmail.com"

# Set the working directory
WORKDIR /app

# Copy the build.gradle and gradle wrapper
COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle

# Copy the source code
COPY src /app/src

# Build the application
RUN ./gradlew build --no-daemon

# Copy the built jar file to the image
COPY build/libs/cicd-0.0.1-SNAPSHOT.jar /app/cicd-0.0.1-SNAPSHOT.jar

# Expose the application port
EXPOSE 8080

# Entry point to run the application
ENTRYPOINT ["java", "-jar", "/app/cicd-0.0.1-SNAPSHOT.jar"]
