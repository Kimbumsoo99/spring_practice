# Base image: OpenJDK 17 with Alpine Linux
FROM bellsoft/liberica-openjdk-alpine:17

# Maintainer info
LABEL maintainer="show7441@gmail.com"

# Set the working directory inside the container
WORKDIR /app

# Copy the build.gradle, settings.gradle, and Gradle wrapper scripts
COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle

# Copy the entire project source code to the working directory
COPY src /app/src

# Grant execution permission to the Gradle wrapper
RUN chmod +x ./gradlew

# Build the application using Gradle
RUN ./gradlew build --no-daemon

# Copy the built JAR file to the image
COPY build/libs/*.jar /app/app.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
