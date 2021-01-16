package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.domain.entities.Review;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewFakeBuilder
{
    private Long          id         = 654321L;
    private long          groupId    = 6432456L;
    private short         visibility = 2;
    private String        review     = "Long reivew text";
    private short         rate       = 3;
    private LocalDateTime createdAt  = LocalDateTime.of(2021, 1, 14, 10, 15, 0);
    private Long          createdBy  = 123L;

    public List<Review> buildAsList()
    {
        return List.of(build());
    }

    public Review build()
    {
        return new Review(id, groupId, visibility, rate, review, createdAt, createdBy);
    }

    public ReviewFakeBuilder setId(Long id)
    {
        this.id = id;

        return this;
    }

    public ReviewFakeBuilder setGroupId(long groupId)
    {
        this.groupId = groupId;

        return this;
    }

    public ReviewFakeBuilder setVisibility(short visibility)
    {
        this.visibility = visibility;

        return this;
    }

    public ReviewFakeBuilder setRate(short rate)
    {
        this.rate = rate;

        return this;
    }

    public ReviewFakeBuilder setReview(String review)
    {
        this.review = review;

        return this;
    }

    public ReviewFakeBuilder setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAt = createdAt;

        return this;
    }

    public ReviewFakeBuilder setCreatedBy(Long createdBy)
    {
        this.createdBy = createdBy;

        return this;
    }
}
