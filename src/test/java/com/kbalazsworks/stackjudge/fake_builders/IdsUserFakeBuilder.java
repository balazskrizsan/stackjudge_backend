package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.ids._entities.IdsUser;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Accessors(fluent = true)
@Getter
@Setter
public class IdsUserFakeBuilder
{
    public static final String  defaultId1                 = "00000000-0000-0000-0000-000000105001";
    public static final String  defaultUserName1           = "IDS UserName";
    public static final String  defaultNormalizedUserName1 = "IDS UserName normalized";
    public static final String  defaultEmail1              = "email@address.com";
    public static final Boolean defaultEmailConfirmed1     = true;
    public static final String  defaultProfilePictureUrl1  = "http://facebook.com/profile.jpg";

    public static final String defaultId2                = "105002";
    public static final String  defaultUserName2           = "Db test user name";
    public static final String  defaultNormalizedUserName2 = "Db test user name";
    public static final String  defaultEmail2              = "";
    public static final Boolean defaultEmailConfirmed2     = false;
    public static final String  defaultProfilePictureUrl2  = "http://facebook.com/profile2.jpg";

    private String  id                  = defaultId1;
    private String  userName            = defaultUserName1;
    private String  normalizedUserName  = defaultNormalizedUserName1;
    private String  email               = defaultEmail1;
    private Boolean emailConfirmed      = defaultEmailConfirmed1;
    private String  profilePictureUrl   = defaultProfilePictureUrl1;

    private String  id2                  = defaultId2;
    private String  userName2            = defaultUserName2;
    private String  normalizedUserName2  = defaultNormalizedUserName2;
    private String  email2               = defaultEmail2;
    private Boolean emailConfirmed2      = defaultEmailConfirmed2;
    private String  profilePictureUrl2   = defaultProfilePictureUrl2;

    public List<IdsUser> buildAsList()
    {
        return List.of(build());
    }

    public Map<String, IdsUser> buildAsMap()
    {
        return Map.of(defaultId1, build());
    }

    public IdsUser build()
    {
        return new IdsUser(id, userName, normalizedUserName, email, emailConfirmed, profilePictureUrl);
    }

    public IdsUser build2()
    {
        return new IdsUser(id2, userName2, normalizedUserName2, email2, emailConfirmed2, profilePictureUrl2);
    }
}
