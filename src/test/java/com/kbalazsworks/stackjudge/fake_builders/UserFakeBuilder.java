package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.state.entities.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(fluent = true)
@Getter
@Setter
public class UserFakeBuilder
{
    public static final Long defaultId1 = 105001L;

    private Long    id                  = defaultId1;
    private Boolean isEmailUser         = false;
    private Boolean isFacebookUser      = true;
    private String  profilePictureUrl   = "http://facebook.com/profile.jpg";
    private String  username            = "";
    private String  password            = "";
    private String  facebookAccessToken = "qweasd123";
    private Long    facebookId          = 123132123L;

    public List<User> buildAsList()
    {
        return List.of(build());
    }

    public User build()
    {
        return new User(
            id,
            isEmailUser,
            isFacebookUser,
            profilePictureUrl,
            username,
            password,
            facebookAccessToken,
            facebookId
        );
    }
}
