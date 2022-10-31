package com.kbalazsworks.stackjudge.domain.company_module.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@ToString
public class Company implements Serializable
{
    @JsonProperty
    private Long          id;
    @JsonProperty
    private String        name;
    @JsonProperty
    private String        domain;
    @JsonProperty
    private short         companySizeId;
    @JsonProperty
    private short         itSizeId;
    @JsonProperty
    private String        logoPath;
    private LocalDateTime createdAt;
    private String        createdBy;
}
