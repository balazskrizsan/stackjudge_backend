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
    public static final Long   defaultId1                = 105001L;
    public static final String defaultUsername1          = "Db test user name";
    public static final String defaultRawPassword1       = "asd";
    public static final String defaultProfilePictureUrl1 = "http://facebook.com/profile.jpg";
    public static final String facebookAccessToken1      = "fb access token";
    public static final long   defaultFacebookId1        = 123456;

    public static final Long   defaultId2                = 105002L;
    public static final String defaultUsername2          = "Db test user 2";
    public static final String defaultRawPassword2       = "asd";
    public static final String defaultProfilePictureUrl2 = "http://facebook.com/profile2.jpg";
    public static final String facebookAccessToken2      = "fb access token 2";
    public static final long   defaultFacebookId2        = 1234562;

    private Long    id                  = defaultId1;
    private Boolean isEmailUser         = true;
    private Boolean isFacebookUser      = false;
    private String  profilePictureUrl   = defaultProfilePictureUrl1;
    private String  username            = defaultUsername1;
    private String  password            = "$2a$10$XeE6D5sueiDx7sT71jz0UezAdVF2.d5q9c1S0/3n.OXaox6RAEDJe"; //asd
    private String  facebookAccessToken = facebookAccessToken1;
    private Long    facebookId          = defaultFacebookId1;

    public List<User> buildAsList()
    {
        return List.of(build());
    }

    public Map<Long, User> buildAsMap()
    {
        return Map.of(defaultId1, build());
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

    public User build2()
    {
        return new User(
            defaultId2,
            isEmailUser,
            isFacebookUser,
            defaultProfilePictureUrl2,
            defaultUsername2,
            password,
            facebookAccessToken2,
            defaultFacebookId2
        );
    }
}
