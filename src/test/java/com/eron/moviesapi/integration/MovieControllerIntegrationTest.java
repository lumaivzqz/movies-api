package com.eron.moviesapi.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import com.eron.moviesapi.exceptions.InvalidThresholdException;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieControllerIntegrationTest {

    private static String MOVIE_API_URL = "/api/directors?threshold=";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetDirectors_withValidThreshold() {
        // Given
        int threshold = 2; 

        // When
        ResponseEntity<List> response = restTemplate.getForEntity(MOVIE_API_URL + threshold, List.class);
        
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<String> directors = response.getBody();
        assertNotNull(directors);
    }

    @Test
    public void testGetDirectors_withEmptyList() {
        // Given
        int threshold = 10<<100;  // A threshold value that results in an empty list

        // When
        ResponseEntity<List> response = restTemplate.getForEntity(MOVIE_API_URL + threshold, List.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<String> directors = response.getBody();
        assertEquals(0, directors.size());
    }

    @Test
    public void testGetDirectors_withInvalidThreshold() {
        // Given
        String invalidThreshold = "invalid";  // An invalid threshold value (non-numeric)

        // When
        ResponseEntity<String> response = restTemplate.getForEntity(MOVIE_API_URL + invalidThreshold, String.class);
       
        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testGetDirectors_withNegativeThreshold() {
        // Given
        int negativeThreshold = -1;  // An invalid threshold value (non-numeric)

        // When
        ResponseEntity<String> response = restTemplate.getForEntity(MOVIE_API_URL + negativeThreshold, String.class);
       
        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(InvalidThresholdException.MESSAGE, response.getBody());
    }

    @Test
    public void testGetDirectors_withZeroThreshold() {
        // Given
        int threshold = 0; // A zero threshold value, which might result in all directors being returned

        // When
        ResponseEntity<List> response = restTemplate.getForEntity(MOVIE_API_URL + threshold, List.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<String> directors = response.getBody();
        assertNotNull(directors);
        assertNotEquals(Arrays.asList(), directors); 
    }
}
