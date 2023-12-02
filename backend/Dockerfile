# Set base image
FROM openjdk:17

# Define an internal variable used only within this Dockerfile
ARG JAR_FILE=build/libs/*.jar

# Copy the JAR file to the container as 'app.jar'
COPY ${JAR_FILE} app.jar

# Copy the current directory contents to the container at /usr/src/app
COPY . /usr/src/app

# Expose port 8000
EXPOSE 8000

# Specify the command to be executed when the container starts
ENTRYPOINT ["java", "-jar", "/app.jar"]
