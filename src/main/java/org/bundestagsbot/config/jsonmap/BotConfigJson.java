package org.bundestagsbot.config.jsonmap;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BotConfigJson {
    @JsonProperty("discord_bot_token")
    private String discordBotToken;
    @JsonProperty("activity_string")
    private String activityString;
    @JsonProperty("command_prefix")
    private String commandPrefix;
    @JsonProperty("suggestion_channels")
    private List<String> suggestionChannels;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    public String getDiscordBotToken() {
        return discordBotToken;
    }

    public String getActivityString() {
        return activityString;
    }

    public String getCommandPrefix() {
        return commandPrefix;
    }

    public List<String> getSuggestionChannels() { return suggestionChannels; }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
