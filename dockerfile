# Start with a base image containing Java runtime
FROM openjdk:11-jdk

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port available to the world outside this container
EXPOSE 8081

# The application's jar file
ARG JAR_FILE=target/dogadoption-1.0.jar

RUN mkdir /api
WORKDIR /api

# Add the application's jar to the container
ADD ${JAR_FILE} /api/dogadoption-1.0.jar

# Run the jar file
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/api/dogadoption-1.0.jar"]