package com.kbalazsworks.stackjudge.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class Company implements Serializable
{
    @JsonProperty private Long          id;
    @JsonProperty private String        name;
    @JsonProperty private String        domain;
    @JsonProperty private short         companySizeId;
    @JsonProperty private short         itSizeId;
    @JsonProperty private String        logoPath;
    private               LocalDateTime createdAt;
    private               Long          createdBy;
}
