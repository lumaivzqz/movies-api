package com.eron.moviesapi.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eron.moviesapi.exceptions.InvalidThresholdException;
import com.eron.moviesapi.services.MovieService;

@RestController
@RequestMapping("/api")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/directors")
    public List<String> getDirectors(@RequestParam int threshold) {
        if (threshold < 0) {
            throw new InvalidThresholdException();
        }

        List<String> directors = movieService.getDirectorsByThreshold(threshold);

        return directors;
    }
}