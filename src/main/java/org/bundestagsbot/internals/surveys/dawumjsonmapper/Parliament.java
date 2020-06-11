package org.bundestagsbot.internals.surveys.dawumjsonmapper;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Parliament {
    @JsonProperty("Shortcut")
    private String shortcut;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Election")
    private String election;

    public String getShortcut() {
        return shortcut;
    }

    public String getName() {
        return name;
    }

    public String getElection() { return election; }

}
