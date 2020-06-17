package org.bundestagsbot.config.guild;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bundestagsbot.config.guild.jsonmap.GuildConfigJson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuildConfigSingleton {
    private static final Logger logger = LogManager.getLogger(GuildConfigSingleton.class.getName());
    private static GuildConfigSingleton instance = null;
    private Map<String, GuildConfig> guildConfigMap = new HashMap<>();

    public GuildConfigSingleton() { }

    public GuildConfigSingleton(List<String> knownGuilds) {
        for (String guildId : knownGuilds ) {
            try {
                guildConfigMap.put(guildId, new GuildConfig(guildId));
            }
            catch(Exception ex) {
                logger.warn("Failed to load config.", ex);
            }
        }
    }

    public static GuildConfigSingleton getInstance() {
        if (instance == null) {
            instance = new GuildConfigSingleton();
        }
        return instance;
    }

    public static void configureInstance(List<String> knownGuilds) {
        for (String guildId : knownGuilds) {
            try {
                getInstance().getGuildConfigMap().put(guildId, new GuildConfig(guildId));
            } catch (IOException ex) {
                logger.warn("Failed to load config.", ex);
            }
        }
    }

    private Map<String, GuildConfig> getGuildConfigMap() {
        return guildConfigMap;
    }

    public static GuildConfigJson getConfig(String guildId) {
        if (!getInstance().getGuildConfigMap().containsKey(guildId)) {
            try {
                getInstance().getGuildConfigMap().put(guildId, new GuildConfig(guildId));
            } catch (IOException ex) {
                logger.warn("Failed to load config.", ex);
            }
        }
        return getInstance().getGuildConfigMap().get(guildId).getConfig();
    }
}
