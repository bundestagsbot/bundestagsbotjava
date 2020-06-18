package org.bundestagsbot.config.global;

import org.bundestagsbot.config.global.jsonmap.GlobalConfigJson;
import org.bundestagsbot.exceptions.ConfigInvalidException;

public class GlobalConfigSingleton {
    private static GlobalConfigSingleton instance = null;
    private GlobalConfig globalConfig;

    private GlobalConfigSingleton() throws ConfigInvalidException {
        try {
            globalConfig = new GlobalConfig();
        }
        catch(Exception ex) {
            throw new ConfigInvalidException("Config initialising failed", ex);
        }
    }

    public GlobalConfig getGlobalConfig() { return globalConfig; }

    public static GlobalConfigSingleton getInstance() {
        if (instance == null) {
            try {
                instance = new GlobalConfigSingleton();
            } catch (ConfigInvalidException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public GlobalConfigJson getConfig() { return getGlobalConfig().getConfig(); }
}
