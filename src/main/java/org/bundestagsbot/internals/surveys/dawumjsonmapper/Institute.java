package org.bundestagsbot.internals.surveys.dawumjsonmapper;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Institute {
    @JsonProperty("Name")
    private String name;

    public String getName() {
        return name;
    }
}
