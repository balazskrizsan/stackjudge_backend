package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.state.entities.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Accessors(fluent = true)
@Getter
@Setter
public class UserFakeBuilder
{
    public static final String defaultId1 = IdsUserFakeBuilder.defaultId1;
    public static final String defaultId2 = IdsUserFakeBuilder.defaultId2;

    private String id = defaultId1;

    public List<User> buildAsList()
    {
        return List.of(build());
    }

    public Map<String, User> buildAsMap()
    {
        return Map.of(defaultId1, build());
    }

    public User build()
    {
        return new User(id);
    }
}
