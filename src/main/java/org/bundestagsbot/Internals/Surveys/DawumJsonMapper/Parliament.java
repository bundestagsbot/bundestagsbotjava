package org.bundestagsbot.Internals.Surveys.DawumJsonMapper;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Parliament {
    @JsonProperty("Shortcut")
    private String shortcut;
    @JsonProperty("Name")
    private String name;

    public String getShortcut() {
        return shortcut;
    }

    public String getName() {
        return name;
    }

    public String getElection() {
        return election;
    }

    private String election;
}
