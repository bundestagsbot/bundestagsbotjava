package org.bundestagsbot.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bundestagsbot.config.jsonmap.BotConfigJson;

import java.io.File;
import java.io.IOException;

public class BotConfig {
    private static final Logger logger = LogManager.getLogger(BotConfig.class.getName());
    private static final String configPath = System.getenv().getOrDefault("CONFIG_BASE_PATH", ".") + "/config.json";
    private BotConfigJson botConfigJson;

    public BotConfig() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        botConfigJson = objectMapper.readValue(new File(configPath), BotConfigJson.class);
    }

    public BotConfigJson getConfig() { return botConfigJson; }
}
