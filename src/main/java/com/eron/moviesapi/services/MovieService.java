package com.eron.moviesapi.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.server.ServerErrorException;

import com.eron.moviesapi.entities.Movie;
import com.eron.moviesapi.exceptions.EronApiException;
import com.eron.moviesapi.services.models.ApiResponse;

@Service
public class MovieService {

    private static final RestTemplate restTemplate = new RestTemplate();

    @Value("${config.api.url}")
    private String MOVIE_API_URL;

    public List<String> getDirectorsByThreshold(int threshold) {
        // Get movies per director
        Map<String, Integer> directorCount = new HashMap<String, Integer>();
        try {
            directorCount.putAll(getDirectorCount());
        } catch (Exception e) {
            // Log something
            throw new ServerErrorException("There were an internal error, try again later", null);
        }
       

        // Filter directors who have directed more than the given threshold
        Set<String> directors = directorCount.entrySet().stream()  
                .filter(entry -> entry.getValue() > threshold) 
                .map(Map.Entry::getKey)  
                .collect(Collectors.toSet()); 

        // Sort the directors alphabetically
        List<String> sortedDirectors = new ArrayList<>(directors);
        Collections.sort(sortedDirectors);

        return sortedDirectors;
    }

    @Scheduled(fixedRate = 600000)  // Runs every 10 minutes (600000 ms)
    public void refreshGetDirectorCountCache() {
        getDirectorCount();
    }

    @Cacheable("getDirectorCountCache") 
    private Map<String, Integer> getDirectorCount() {
        Map<String, Integer> directorCount = new HashMap<>();

        int currentPage = 1;

        ApiResponse response = this.getMoviesFromPage(currentPage);

        int totalPages = response.getTotalPages();

        while (currentPage <= totalPages && response != null && !response.getData().isEmpty()) {
            for (Movie movie : response.getData()) {
                String director = movie.getDirector();
                if (director != null && !director.isEmpty()) {
                    directorCount.put(director, directorCount.getOrDefault(director, 0) + 1);
                }
            }

            currentPage++;
        }

        return directorCount;
    }

    // Method to fetch movie data for a specific page
    private ApiResponse getMoviesFromPage(int pageNumber) throws EronApiException {
        String url = MOVIE_API_URL + pageNumber;
         try {
            return restTemplate.getForObject(url, ApiResponse.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Log something
            String errorMessage = "Error fetching data from the API: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
            throw new EronApiException(errorMessage, e);
        } catch (RestClientException e) {
            // Log something
            String errorMessage = "Error during API request: " + e.getMessage();
            throw new EronApiException(errorMessage, e);
        } catch (Exception e) {
            // Log something
            String errorMessage = "Unexpected error occurred while fetching movies: " + e.getMessage();
            throw new EronApiException(errorMessage, e);
        }   
    }
}