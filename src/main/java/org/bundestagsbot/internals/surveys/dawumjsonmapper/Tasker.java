package org.bundestagsbot.internals.surveys.dawumjsonmapper;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tasker {
    @JsonProperty("Name")
    private String name;

    public String getName() {
        return name;
    }
}
