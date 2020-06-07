package org.bundestagsbot.Config;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bundestagsbot.Discord.DiscordClient;
import org.bundestagsbot.LocalUtils.ArrayObjectUtil;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Config {

    private static final Logger logger = LogManager.getLogger(Config.class.getName());
    private static JSONObject cfg;

    public static JSONObject getCfg() {
        return cfg;
    }

    public static Object get(Object key) throws NullPointerException{
        if (!getCfg().containsKey(key)) {
            logger.warn("Did not find config key \"" + key.toString() + "\". Please update your config.");
            return null;
        }
        return getCfg().get(key);
    }

    public static Object get(Object key, Object defaultValue) {
        if (!getCfg().containsKey(key)) {
            return defaultValue;
        }
        return get(key);
    }

    public static List<?> getArray(Object key){
        Object value = get(key, null);
        if (value == null) {
            return null;
        }
        return ArrayObjectUtil.convertObjectToList(value);
    }

    public static boolean loadConfig() {
        try {
            logger.info("Loading config.");
            Object obj = new JSONParser().parse(new FileReader("config.json"));
            cfg = (JSONObject) obj;
            logger.info("Found " + cfg.keySet().size() + " entries.");
        } catch(IOException | ParseException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    public static boolean generateConfig() {
        logger.info("Generating config.json");
        File config = new File("./config.json");
        if (config.exists()) {
            logger.info("config.json already exists.");
            return true;
        }

        JSONObject jsonCFG = new JSONObject();
        jsonCFG.put("discord_bot_token", "bot_token_goes_here");
        jsonCFG.put("activity_string", null);
        jsonCFG.put("bot_command_channels", new ArrayList<String>());
        jsonCFG.put("command_prefix", "-");

        try {
            FileWriter writer = new FileWriter(config);
            writer.write(jsonCFG.toJSONString());
            writer.close();
        } catch(IOException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
