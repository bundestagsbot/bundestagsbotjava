package org.bundestagsbot.internals.surveys.dawumjsonmapper;

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
