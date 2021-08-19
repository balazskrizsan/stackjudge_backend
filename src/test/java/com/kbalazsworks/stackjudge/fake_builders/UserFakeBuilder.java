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
    public static final Long   defaultId1               = 105001L;
    public static final String defaultUsername          = "Db test user name";
    public static final String defaultRawPassword       = "asd";
    public static final String defaultProfilePictureUrl = "http://facebook.com/profile.jpg";

    private Long    id                  = defaultId1;
    private Boolean isEmailUser         = true;
    private Boolean isFacebookUser      = false;
    private String  profilePictureUrl   = defaultProfilePictureUrl;
    private String  username            = defaultUsername;
    private String  password            = "$2a$10$XeE6D5sueiDx7sT71jz0UezAdVF2.d5q9c1S0/3n.OXaox6RAEDJe"; //asd
    private String  facebookAccessToken = "fb access token";
    private Long    facebookId          = 123456L;

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
