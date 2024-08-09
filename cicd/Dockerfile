# Base image
FROM bellsoft/liberica-openjdk-alpine:17

# Set the working directory
WORKDIR /app

# Copy Gradle wrapper and build configuration files
COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle

# Ensure the Gradle wrapper script has execute permissions
RUN chmod +x /app/gradlew

# Copy the entire project source code
COPY src /app/src

# Verify the existence and permissions of gradlew
RUN ls -l /app && echo "Gradle wrapper exists" || echo "Gradle wrapper does not exist"

# Build the application without running tests
RUN ./gradlew bootJar --no-daemon -x test

# Copy the built JAR file to the image
COPY build/libs/*.jar /app/app.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
