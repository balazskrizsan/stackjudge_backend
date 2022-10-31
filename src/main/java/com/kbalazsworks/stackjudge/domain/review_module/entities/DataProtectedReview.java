package com.kbalazsworks.stackjudge.domain.review_module.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonProperty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class DataProtectedReview {
    @JsonProperty("viewerIdsUserId") private String viewerIdsUserId;
}
