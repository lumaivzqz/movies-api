package com.eron.moviesapi.services.models;

import java.util.List;

import com.eron.moviesapi.entities.Movie;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponse {
    
    private int page;
    
    @JsonProperty("per_page")
    private int perPage;
    
    private int total;

    @JsonProperty("total_pages")
    private int totalPages;

    private List<Movie> data;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Movie> getData() {
        return data;
    }

    public void setData(List<Movie> data) {
        this.data = data;
    }
}