package org.bundestagsbot.Internals.Surveys.DawumJsonMapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.Map;

public class RootDawumJson {

    @JsonProperty("Database")
    private DatabaseInfo database;
    @JsonProperty("Parliaments")
    private Map<String, Parliament> parliaments;
    @JsonProperty("Institutes")
    private Map<String, Institute> institutes;
    @JsonProperty("Taskers")
    private Map<String, Tasker> taskers;
    @JsonProperty("Parties")
    private Map<String, Party> parties;
    @JsonProperty("Surveys")
    private Map<String, Survey> surveys;


    public DatabaseInfo getDatabase() {
        return database;
    }

    public Map<String, Survey> getSurveyMap() { return surveys; }
    public Collection<Survey> getSurveys() {
        return surveys.values();
    }
    public Survey getSurvey(String id) {
        return surveys.get(id);
    }

    public Collection<Party> getParties() {
        return parties.values();
    }
    public Party getParty(String id) {
        return parties.get(id);
    }

    public Map<String, Parliament> getParliamentMap() { return parliaments; }
    public Collection<Parliament> getParliaments() {
        return parliaments.values();
    }
    public Parliament getParliament(String id) {
        return parliaments.get(id);
    }

    public Collection<Institute> getInstitutes() {
        return institutes.values();
    }
    public Institute getInstitute(String id) {
        return institutes.get(id);
    }

    public Collection<Tasker> getTaskers() {
        return taskers.values();
    }
    public Tasker getTasker(String id) {
        return taskers.get(id);
    }
}
