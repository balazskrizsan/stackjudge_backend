package com.kbalazsworks.stackjudge.domain.entities.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonProperty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DataProtectedReview {
    @JsonProperty("viewerUserId") private long viewerUserId;
}
