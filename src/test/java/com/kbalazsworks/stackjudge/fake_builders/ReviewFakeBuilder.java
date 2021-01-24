package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.domain.entities.Review;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewFakeBuilder
{
    public static final Long defaultId1 = 102001L;
    public static final Long defaultId2 = 102002L;
    public static final Long defaultId3 = 102003L;
    public static final Long defaultId4 = 102004L;
    public static final Long defaultId5 = 102005L;
    public static final Long defaultId6 = 102006L;
    public static final Long defaultId7 = 102007L;
    public static final Long defaultId8 = 102008L;
    public static final Long defaultId9 = 102009L;
    public static final Long defaultId10 = 102010L;

    private Long          id         = defaultId1;
    private long          groupId    = 101001L;
    private short         visibility = 1;
    private short         rate       = 2;
    private String        review     = "long review text";
    private LocalDateTime createdAt  = LocalDateTime.of(2021, 1, 16, 1, 50, 1);
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
