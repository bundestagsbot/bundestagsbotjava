package org.bundestagsbot.Internals.Surveys.DawumJsonMapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.Map;

public class Survey {
    @JsonProperty("Date")
    private String date;
    @JsonProperty("Survey_Period")
    private Map<String, String> survey_period;
    @JsonProperty("Surveyed_Persons")
    private String surveyed_persons;

    public String getDate() {
        return date;
    }

    public Map<String, String> getSurvey_period() {
        return survey_period;
    }

    public String getSurveyed_persons() {
        return surveyed_persons;
    }

    public String getParliament_id() {
        return parliament_id;
    }

    public String getTasker_id() {
        return tasker_id;
    }

    public Collection<Integer> getResults() {
        return results;
    }

    private String parliament_id;
    private String tasker_id;
    private Collection<Integer> results;
}
