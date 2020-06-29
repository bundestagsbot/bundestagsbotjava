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
    @JsonProperty("welcome_message")
    private String welcomeMessage;
    @JsonProperty("suggestion_channels")
    private List<String> suggestionChannels;
    @JsonProperty("role_reaction_channels")
    private List<String> roleReactionChannels;
    @JsonProperty("role_on_reaction")
    private Map<String, String> roleOnReaction;
    @JsonProperty("assignable_roles")
    private List<String> assignableRoles;
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

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public List<String> getSuggestionChannels() { return suggestionChannels; }

    public List<String> getRoleReactionChannels() { return roleReactionChannels; }

    public Map<String, String> getRoleOnReaction() { return roleOnReaction; }

    public List<String> getAssignableRoles() { return assignableRoles; }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
