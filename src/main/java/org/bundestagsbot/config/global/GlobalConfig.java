package org.bundestagsbot.config.global;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bundestagsbot.config.global.jsonmap.GlobalConfigJson;

import java.io.File;
import java.io.IOException;

public class GlobalConfig {
    private static final Logger logger = LogManager.getLogger(GlobalConfig.class.getName());
    private static final String configPath = System.getenv().getOrDefault("CONFIG_BASE_PATH", ".") + "/config.json";
    private GlobalConfigJson globalConfigJson;

    public GlobalConfig() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        globalConfigJson = objectMapper.readValue(new File(configPath), GlobalConfigJson.class);
    }

    public GlobalConfigJson getConfig() { return globalConfigJson; }
}
