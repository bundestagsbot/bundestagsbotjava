package org.bundestagsbot.config;

import org.bundestagsbot.config.jsonmap.BotConfigJson;
import org.bundestagsbot.exceptions.ConfigInvalidException;

public class BotConfigSingleton {
    private static BotConfigSingleton instance = null;
    private BotConfig botConfig;

    private BotConfigSingleton() throws ConfigInvalidException {
        try {
            botConfig = new BotConfig();
        }
        catch(Exception ex) {
            throw new ConfigInvalidException("Config initialising failed", ex);
        }
    }

    public BotConfig getBotConfig() { return botConfig; }

    public static BotConfigSingleton getInstance() {
        if (instance == null) {
            try {
                instance = new BotConfigSingleton();
            } catch (ConfigInvalidException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public BotConfigJson getConfig() { return getBotConfig().getConfig(); }
}
