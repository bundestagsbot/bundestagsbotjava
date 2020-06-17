package org.bundestagsbot.config.guild;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bundestagsbot.config.global.GlobalConfig;
import org.bundestagsbot.config.guild.jsonmap.GuildConfigJson;

import java.io.File;
import java.io.IOException;

public class GuildConfig {
    private final static String configBasePath = System.getenv().getOrDefault("CONFIG_BASE_PATH", ".") + "/guilds/";
    private GuildConfigJson guildConfigJson;

    public GuildConfig(String guildId) throws IOException {
        String configPath = configBasePath + guildId + "/config.json";
        File configFile = new File(configPath);
        if (!configFile.exists()) {
            generateConfig(configPath);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        guildConfigJson = objectMapper.readValue(new File(configPath), GuildConfigJson.class);
    }

    public GuildConfigJson getConfig() { return guildConfigJson; }

    private void generateConfig(String configPath) throws IOException {
        File outFile = new File(configPath);
        File outDir = new File(outFile.getParent());
        if (!outDir.mkdirs()) {
            throw new IOException("Failed to create config directories.");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.writeValue(new File(configPath), new GuildConfigJson());
    }
}
