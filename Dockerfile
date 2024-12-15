# Step 1: Use a base image that includes JDK (OpenJDK)
FROM openjdk:17-jdk-slim AS base

# Step 2: Set the working directory inside the container
WORKDIR /src

# Step 3: Copy the JAR file into the container (replace with your actual JAR filename)
COPY target/moviesapi-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Expose the port on which the application will run
EXPOSE 8080

# Step 5: Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]