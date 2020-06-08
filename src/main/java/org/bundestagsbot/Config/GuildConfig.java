package org.bundestagsbot.Config;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bundestagsbot.LocalUtils.ArrayObjectUtil;
import org.bundestagsbot.LocalUtils.FileSystem;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GuildConfig {

    private static final Logger logger = LogManager.getLogger(GuildConfig.class.getName());
    private final static String configPath = System.getenv().getOrDefault("CONFIG_PATH", ".") + "/guilds/";
    private static Map<String, JSONObject> guildcfgs = new HashMap<>();

    public static JSONObject getGuildCfg(String guildID) {

        if(guildcfgs.containsKey(guildID)){
            return guildcfgs.get(guildID);
        }
        else {
            return loadGuildConfig(guildID);

        }
    }


    public static Object get(Object key, String guildID) throws NullPointerException{
        if (!getGuildCfg(guildID).containsKey(key)) {
            logger.warn("Did not find config key \"" + key.toString() + "\". Please update your config.");
            return null;
        }
        return getGuildCfg(guildID).get(key);
    }

    public static Object get(Object key, Object defaultValue, String guildID) {
        if (!getGuildCfg(guildID).containsKey(key)) {
            return defaultValue;
        }
        return get(key, guildID);
    }


    public static List<?> getArray(Object key){
        Object value = get(key, null);
        if (value == null) {
            return null;
        }
        return ArrayObjectUtil.convertObjectToList(value);
    }

    private static JSONObject loadGuildConfig(String guildID) {

        logger.info("Loading config.");
        Optional<JSONObject> cfgOptional = FileSystem.loadJson(configPath + guildID + "/config.json");

        if(cfgOptional.isEmpty()) {
            generateGuildConfig(guildID);
            return loadGuildConfig(guildID);  // TODO: rework this recursion
        }
        else {
            JSONObject cfg = cfgOptional.get();
            logger.info("Found " + cfg.keySet().size() + " entries.");
            guildcfgs.put(guildID, cfg);
            return cfg;
        }
    }

    @SuppressWarnings("unchecked")
    public static void generateGuildConfig(String guildID) {
        logger.info("Generating guild config.json for guild: \"" + guildID + "\".");
        //Todo: verify following line
        File config = new File(configPath + guildID + "/config.json");
        if (config.exists()) {
            logger.info("config.json for the guild ID: \"" + guildID + "\" already exists.");
            return;
        }

        JSONObject jsonCFG = new JSONObject();

        jsonCFG.put("bot_command_channels", new ArrayList<String>());
        jsonCFG.put("command_prefix", "_");

        try {
            FileSystem.saveJson(configPath + guildID + "/config.json", jsonCFG);
        } catch (IOException ex) {
            logger.warn("Failed to save guild config.json", ex);
        }
    }
}
