# Eron Back-End Developer Challenge 

This project is a solution for the Eron Back-End Developer Challenge, built using Java and Spring Boot. The application is designed to retrieve and process movie data from an external API.

## Technologies Used
- Java (Version 17)
- Spring Boot (For building the REST API)
- Maven (For project management and building)
- Docker (For containerization)
- Mockito (For unit and integration tests)

## Project Overview
The application provides an endpoint to retrieve a list of directors who have directed more than a specified number of movies. The data is fetched from an external API, and the application processes this data to return the desired list.

## Key Features
- Movie Data Fetching: The application communicates with an external movie API to retrieve movie data.
- Director Filter: It filters directors based on the number of movies they have directed, providing a list of directors who have directed more than the specified threshold.
- Unit and Integration Testing: Unit tests were created for the controller and integration tests to ensure the functionality of the service layer.
- Error Handling: The service handles API errors gracefully by throwing custom exceptions in case of failures.

## Testing
- Unit Tests: Unit tests were created for the controller layer using Mockito. However, due to private methods in the service layer, unit tests for the service layer were omitted. The service is considered covered by the integration tests.
- Integration Tests: Full integration tests were created to test the entire flow of fetching and processing movie data.

## How to Run the Project
You have two options to run this project:

### Option 1: Running with Maven
1. Ensure Java 17 and Maven are installed on your machine.

2. Run the following commands in your terminal:
``
mvn clean install
mvn spring-boot:run
``

4. The application will be available at http://localhost:8080.

### Option 2: Running with Docker
1. Ensure Docker is installed and running on your machine.

2. Clone the repository.

3. Build the Docker image:
``
docker build -t movies-app .
``
4. Run the Docker container:
``
docker run -p 8080:8080 movies-app
``

5. The application will be available at http://localhost:8080.

### Available Endpoints
- GET /directors: Retrieve a list of directors who have directed more than a given threshold of movies.
- Query parameter: threshold (e.g., GET /directors?threshold=5)

### Assumptions and Considerations
- The service uses a third-party external API to fetch movie data. In production, this API endpoint would need to be reliable and performant.
- Error handling has been implemented to ensure that any issues with fetching data from the external API are handled gracefully.
- The service layer contains private methods, which I did not write unit tests for directly. I have covered this by writing integration tests that verify the behavior of the service as a whole.

### Final Thoughts
I built this solution with simplicity in mind, while ensuring that it can be easily extended and scaled if needed. The application is designed to be easily containerized using Docker, allowing it to run consistently across different environments.

Looking forward to your feedback!