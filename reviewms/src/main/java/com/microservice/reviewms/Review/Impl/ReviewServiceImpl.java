package com.microservice.reviewms.Review.Impl;

import com.microservice.reviewms.Review.Review;
import com.microservice.reviewms.Review.ReviewRepository;
import com.microservice.reviewms.Review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService
{
    private final ReviewRepository reviewRepository;
//removing the company dependency bcs microservice should
// be standalone and should not be dependent

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAllReviews(Long companyId)
    {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return reviews;
    }

    @Override
    public boolean addReview(Long companyId, Review review)
    {
        if(companyId != null && review != null)
        {
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);

    }

    @Override
    public boolean updateReview(Long reviewId, Review updatedReview) {

        Review review = reviewRepository.findById(reviewId).orElse(null);

        if (review != null) {
            updatedReview.setTitle(updatedReview.getTitle());
            updatedReview.setDescription(updatedReview.getDescription());
            updatedReview.setRating(updatedReview.getRating());
            updatedReview.setCompanyId(updatedReview.getCompanyId());
            updatedReview.setId(reviewId);
            reviewRepository.save(review);
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);

        if (review != null) {
            reviewRepository.delete(review);
           return true;
        }
        else
            return false;
    }


}
