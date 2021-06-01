package com.kbalazsworks.stackjudge.api.value_objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class FacebookUser
{
    private @JsonProperty("id")                  Long    id;
    private @JsonProperty("name")                String  name;
    private @JsonProperty("pictureHeight")       Integer pictureHeight;
    private @JsonProperty("pictureIsSilhouette") boolean pictureIsSilhouette;
    private @JsonProperty("pictureUrl")          String  pictureUrl;
    private @JsonProperty("pictureWidth")        Integer pictureWidth;

    @SuppressWarnings("unchecked")
    @JsonProperty("picture")
    private void unpackNested(Map<String, Object> picture)
    {
        Map<String, Object> data = (Map<String, Object>) picture.get("data");

        this.pictureHeight       = (Integer) data.get("height");
        this.pictureIsSilhouette = (boolean) data.get("is_silhouette");
        this.pictureUrl          = String.valueOf(data.get("url"));
        this.pictureWidth        = (Integer) data.get("width");
    }

    public Long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public Integer getPictureHeight()
    {
        return pictureHeight;
    }

    public boolean isPictureIsSilhouette()
    {
        return pictureIsSilhouette;
    }

    public String getPictureUrl()
    {
        return pictureUrl;
    }

    public Integer getPictureWidth()
    {
        return pictureWidth;
    }
}
