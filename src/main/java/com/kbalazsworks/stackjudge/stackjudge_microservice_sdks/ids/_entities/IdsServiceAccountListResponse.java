package com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.ids._entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbalazsworks.stackjudge.state.entities.IdsUser;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.processing.Generated;
import java.util.List;

@Generated("OpenSDK: https://github.com/balazskrizsan/OpenSdk")
@Jacksonized
@Builder(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter
@ToString
public class IdsServiceAccountListResponse
{
    @JsonProperty("extendedUsers")
    private final List<IdsUser> extendedUsers;
}
