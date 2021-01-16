package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.domain.entities.Review;
import com.kbalazsworks.stackjudge.domain.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService
{
    private ReviewRepository reviewRepository;

    @Autowired
    public void setReviewRepository(ReviewRepository reviewRepository)
    {
        this.reviewRepository = reviewRepository;
    }

    public void create(Review review)
    {
        reviewRepository.create(review);
    }

    public Map<Long, Map<Long, List<Review>>> search(List<Long> companiesIds)
    {
        return new HashMap<>();
    }
}
