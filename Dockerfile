# Use a base image with JDK
FROM openjdk:17-jdk-alpine

# Set the work directory in the container
WORKDIR /app

# Copy the Spring Boot application JAR file into the container
COPY target/rentease-server.jar rentease-server.jar

# Expose the port on which the app will run
EXPOSE 8000

# Command to run the application
ENTRYPOINT ["java", "-jar", "rentease-server.jar"]
