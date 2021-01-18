package com.kbalazsworks.stackjudge.domain.services;

import com.google.common.collect.Lists;
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
        Map<Long, Map<Long, List<Review>>> result = new HashMap<>();
        reviewRepository.search(companiesIds).forEach(
            (companyId, reviews) ->
            {
                Map<Long, List<Review>> groups = new HashMap<>();

                reviews.forEach(
                    review ->
                    {
                        List<Review> list = groups.get(review.groupId());
                        if (null == list)
                        {
                            groups.put(review.groupId(), Lists.newArrayList(review));

                            return;
                        }

                        list.add(review);
                    }
                );

                result.put(companyId, groups);
            }
        );

        return result;
    }

    public void delete(long companyId)
    {
        reviewRepository.delete(companyId);
    }
}
