# Base image: OpenJDK 17 with Alpine Linux
FROM bellsoft/liberica-openjdk-alpine:17

# Maintainer info
LABEL maintainer="show7441@gmail.com"

# Set the working directory inside the container
WORKDIR /app

# Copy the build.gradle, settings.gradle, and Gradle wrapper scripts
COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle

# Ensure the Gradle wrapper script has execute permissions
RUN chmod +x /app/gradlew

# Install curl to download the Gradle Wrapper if it does not exist
RUN apk add --no-cache curl

# If gradlew does not exist, generate the Gradle Wrapper
RUN if [ ! -f "/app/gradlew" ]; then \
        curl -sSLO https://services.gradle.org/distributions/gradle-8.8-bin.zip && \
        unzip gradle-8.8-bin.zip && \
        rm gradle-8.8-bin.zip && \
        ./gradle-8.8/bin/gradle wrapper; \
    fi

# Copy the entire project source code to the working directory
COPY src /app/src

# Build the application without running tests
RUN /app/gradlew build --no-daemon -x test

# Copy the built JAR file to the image
COPY build/libs/*.jar /app/app.jar

# Expose the application port
EXPOSE 8080

# Environment variables for runtime configuration (can be overridden by Jenkins)
ENV SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/ssafytest?useSSL=false&useUnicode=true&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true
ENV SPRING_DATASOURCE_USERNAME=ssafy
ENV SPRING_DATASOURCE_PASSWORD=ssafy

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
