package com.kbalazsworks.stackjudge.fake_builders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbalazsworks.stackjudge.api.value_objects.FacebookUser;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
@Setter
public class FacebookUserFakeBuilder
{
    private Long    id                  = 900001L;
    private String  name                = IdsUserFakeBuilder.defaultUserName1;
    private Integer pictureHeight       = 100;
    private boolean pictureIsSilhouette = true;
    private String  pictureUrl          = IdsUserFakeBuilder.defaultProfilePictureUrl1;
    private Integer pictureWidth        = 200;

    @SneakyThrows
    public FacebookUser build()
    {
        // @formatter:off
        String data =
"{" +
    "\"id\":\""    + id + "\"," +
    "\"name\":\""  + name + "\"," +
    "\"picture\":" +
    "{" +
        "\"data\":" +
        "{" +
            "\"height\":"        + pictureHeight + "," +
            "\"is_silhouette\": " + pictureIsSilhouette + "," +
            "\"url\":\""          + pictureUrl + "\"," +
            "\"width\": "         + pictureWidth +
        "}" +
    "}" +
"}";
        // @formatter:on

        return new ObjectMapper().readValue(data, FacebookUser.class);
    }
}
