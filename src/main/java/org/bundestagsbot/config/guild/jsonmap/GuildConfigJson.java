package org.bundestagsbot.config.guild.jsonmap;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuildConfigJson {
    @JsonProperty("bot_command_channels")
    private List<String> botCommandChannels;
    @JsonProperty("command_prefix")
    private String commandPrefix;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    public List<String> getBotCommandChannels() {
        return botCommandChannels;
    }

    public String getCommandPrefix() {
        return commandPrefix;
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
