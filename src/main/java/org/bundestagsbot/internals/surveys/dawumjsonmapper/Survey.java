package org.bundestagsbot.internals.surveys.dawumjsonmapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Survey {
    @JsonProperty("Date")
    private String date;
    @JsonProperty("Survey_Period")
    private Map<String, String> surveyPeriod;
    @JsonProperty("Surveyed_Persons")
    private String surveyedPersons;
    @JsonProperty("Parliament_ID")
    private String parliamentId;
    @JsonProperty("Tasker_ID")
    private String taskerId;
    @JsonProperty("Results")
    private Map<String, Integer> results;
    @JsonProperty("Institute_ID")
    private String instituteId;

    public String getInstituteId() { return instituteId; }

    public String getDate() {
        return date;
    }

    public Map<String, String> getSurveyPeriod() {
        return surveyPeriod;
    }

    public String getSurveyedPersons() {
        return surveyedPersons;
    }

    public String getParliamentId() {
        return parliamentId;
    }

    public String getTaskerId() {
        return taskerId;
    }

    public Map<String, Integer> getResults() {
        return results;
    }

}
