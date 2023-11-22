# Set base image
FROM openjdk:17

# Define an internal variable used only within this Dockerfile
ARG JAR_FILE=build/libs/*.jar

# Copy the file defined by JAR_FILE variable to the container as 'app.jar'
COPY ${JAR_FILE} app.jar

# Expose port 8000
EXPOSE 8000

# Specify the command to be executed when the container starts (can also specify a script file)
ENTRYPOINT ["java", "-jar", "/app.jar"]
