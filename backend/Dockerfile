# Set base image to Ubuntu
FROM ubuntu:latest

# Install OpenJDK 17
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk && \
    apt-get clean;

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy the current directory contents to the container at /usr/src/app
COPY . /usr/src/app

# Expose port 8000
EXPOSE 8080

# Start the Java application
CMD ["java", "-jar", "/usr/src/app/build/libs/se7-0.0.1-SNAPSHOT.jar"]

# If you want to keep the container running uncomment the following line
#CMD tail -f /dev/null
