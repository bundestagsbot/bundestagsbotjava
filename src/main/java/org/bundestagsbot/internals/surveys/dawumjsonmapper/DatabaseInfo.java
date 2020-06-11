package org.bundestagsbot.internals.surveys.dawumjsonmapper;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class DatabaseInfo {
    @JsonProperty("License")
    private LicenseInfo license;
    @JsonProperty("Publisher")
    private String publisher;
    @JsonProperty("Author")
    private String author;
    @JsonProperty("Last_Update")
    private String lastUpdate;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("License")
    public LicenseInfo getLicense() {
        return license;
    }

    @JsonProperty("License")
    public void setLicense(LicenseInfo license) {
        this.license = license;
    }

    @JsonProperty("Publisher")
    public String getPublisher() {
        return publisher;
    }

    @JsonProperty("Publisher")
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @JsonProperty("Author")
    public String getAuthor() {
        return author;
    }

    @JsonProperty("Author")
    public void setAuthor(String author) {
        this.author = author;
    }

    @JsonProperty("Last_Update")
    public String getLastUpdate() {
        return lastUpdate;
    }

    @JsonProperty("Last_Update")
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
