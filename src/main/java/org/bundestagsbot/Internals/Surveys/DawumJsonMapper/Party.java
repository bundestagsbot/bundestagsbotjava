package org.bundestagsbot.Internals.Surveys.DawumJsonMapper;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Party {
    @JsonProperty("Shortcut")
    private String shortcut;
    @JsonProperty("Name")
    private String name;

    public String getName() {
        return name;
    }

    public String getShortcut() {
        return shortcut;
    }
}
