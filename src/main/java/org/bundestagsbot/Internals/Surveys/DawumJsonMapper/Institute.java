package org.bundestagsbot.Internals.Surveys.DawumJsonMapper;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Institute {
    @JsonProperty("Name")
    private String name;

    public String getName() {
        return name;
    }
}
