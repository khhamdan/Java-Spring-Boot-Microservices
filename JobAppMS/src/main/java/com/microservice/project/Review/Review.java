package com.microservice.project.Review;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.microservice.project.Company.Company;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Review
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private double rating;

    @JsonIgnore
    @ManyToOne
    private Company company;

    @OneToMany
    private List<Review> reviews;

    public Review() {
    }

    public Review(Long id, String title, String description, double rating) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rating = rating;
    }
}
