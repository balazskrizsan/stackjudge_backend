package com.kbalazsworks.stackjudge.domain.review_module.services;

import com.kbalazsworks.stackjudge.domain.entities.Review;
import com.kbalazsworks.stackjudge.domain.review_module.enums.VisibilityEnum;
import com.kbalazsworks.stackjudge.domain.review_module.repositories.ReviewRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService
{
    private final ReviewRepository reviewRepository;

    public void create(@NonNull Review review)
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
                            groups.put(review.groupId(), new ArrayList<>(List.of(review)));

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


    public Map<Long, Map<Long, List<Review>>> maskProtectedReviewCreatedBys(Map<Long, Map<Long, List<Review>>> companyReviews)
    {
        companyReviews.forEach((companyId, items) -> items.forEach((groupId, reviews) -> {
            List<Review> masked = reviews
                .stream()
                .map(r -> r.visibility() != VisibilityEnum.PROTECTED.getValue() ? r : new Review(
                    r.id(),
                    r.groupId(),
                    r.visibility(),
                    r.rate(),
                    r.review(),
                    r.createdAt(),
                    0L
                ))
                .collect(Collectors.toList());
            items.put(groupId, masked);
        }));

        return companyReviews;
    }
}
