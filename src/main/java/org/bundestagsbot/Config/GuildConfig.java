package org.bundestagsbot.Config;

import org.bundestagsbot.LocalUtils.ArrayObjectUtil;
import org.bundestagsbot.LocalUtils.FileSystem;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class GuildConfig {

    private final static Logger LOGGER = Logger.getLogger(GuildConfig.class.getName());

    private static HashMap<String, JSONObject> guildcfgs = new HashMap<>();

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
            LOGGER.warning("Did not find config key \"" + key.toString() + "\". Please update your config.");
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

        LOGGER.info("Loading config.");
        JSONObject cfg = FileSystem.loadJson("./guilds/"+guildID+"/config.json");

        if(cfg == null) {

            generateGuildConfig(guildID);
            return loadGuildConfig(guildID);

        }
        LOGGER.info("Found " + cfg.keySet().size() + " entries.");
        guildcfgs.put(guildID, cfg);
        return cfg;
    }

    @SuppressWarnings("unchecked")
    public static void generateGuildConfig(String guildID) {
        LOGGER.info("Generating config.json");
        //Todo: verify following line
        File config = new File("./guilds/"+guildID+"/config.json");
        if (config.exists()) {
            LOGGER.info("config.json for the guild ID:"+guildID+" already exists.");
            return;
        }

        JSONObject jsonCFG = new JSONObject();

        jsonCFG.put("bot_command_channels", new ArrayList<String>());
        jsonCFG.put("command_prefix", "-");

        FileSystem.saveJson("./guilds/" + guildID + "/config.json", jsonCFG);
    }
}
