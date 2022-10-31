package com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.ids._entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Getter(onMethod = @__(@JsonIgnore))
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IdsUser implements Serializable
{
    @Getter
    @JsonProperty("sub")
    @JsonAlias("id")
    private String  id;
    @Getter
    @JsonProperty("userName")
    private String  userName;
    @Getter
    @JsonProperty("normalizedUserName")
    private String  normalizedUserName;
    @JsonProperty("email")
    private String  email;
    @JsonProperty("emailConfirmed")
    private Boolean emailConfirmed;
    @Getter
    @JsonProperty("profileUrl")
    private String  profilePictureUrl;
}
