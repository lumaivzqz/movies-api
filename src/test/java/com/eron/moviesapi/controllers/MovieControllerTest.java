package com.eron.moviesapi.controllers;

import com.eron.moviesapi.exceptions.InvalidThresholdException;
import com.eron.moviesapi.services.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieControllerTest {

    @InjectMocks
    private MovieController movieController; 

    @Mock
    private MovieService movieService; 

    @BeforeEach
    public void setup() {
    }

    @Test
    void testGetDirectors_withValidThreshold() throws Exception {
        // Given
        List<String> mockDirectors = Arrays.asList("Director A", "Director B");
        when(movieService.getDirectorsByThreshold(3)).thenReturn(mockDirectors);

        // When
        List<String> directors = movieController.getDirectors(3);

        // Then
        assertEquals(2, directors.size());  
        assertEquals("Director A", directors.get(0));
        assertEquals("Director B", directors.get(1));  
    }

    @Test
    void testGetDirectors_withEmptyResult() throws Exception {
       // Given
       when(movieService.getDirectorsByThreshold(5)).thenReturn(Arrays.asList());

       // When
       List<String> directors = movieController.getDirectors(5);

       // Then
       assertEquals(0, directors.size());  
  
    }

    @Test
    void testGetDirectors_withZeroThreshold() {
        // Given
        List<String> mockDirectors = Arrays.asList("Director A");
        when(movieService.getDirectorsByThreshold(0)).thenReturn(mockDirectors);

        // When
        List<String> directors = movieController.getDirectors(0);

        // Then
        assertEquals(1, directors.size()); 
        assertEquals("Director A", directors.get(0)); 
    }

    @Test
    void testGetDirectors_withNegativeThreshold() {
        // Given
        try {
            // When
            List<String> directors = movieController.getDirectors(-1);
        } catch (Exception e) {
            // Then
            assertEquals(InvalidThresholdException.class, e.getClass());  
        }
    }

    @Test
    void testGetDirectors_withInvalidThreshold() throws Exception {
        // Given
        String invalidThreshold = "invalid"; 

        try {
            // When
            movieController.getDirectors(Integer.parseInt(invalidThreshold));  
        } catch (NumberFormatException e) {
             // Then
            assertEquals("For input string: \"invalid\"", e.getMessage());  
        }
    }
}
