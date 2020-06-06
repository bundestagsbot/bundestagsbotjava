package org.bundestagsbot;

import org.bundestagsbot.Config.Config;
import org.bundestagsbot.Meta.About;
import org.bundestagsbot.Discord.DiscordClient;
import org.bundestagsbot.Discord.DiscordConnectionHandling;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class BundestagsBot {
    private final static Logger LOGGER = Logger.getLogger(BundestagsBot.class.getName());

    public static void setDebugLevel(Level newLvl) {
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(newLvl);
        for (Handler h : rootLogger.getHandlers()) {
            System.out.println(h.toString());
            h.setLevel(newLvl);
        }
    }

    public static void main(String[] args) {
        setDebugLevel(Level.INFO);
        LOGGER.info("Starting up.");
        LOGGER.info(About.getInfo());

        LOGGER.fine("Initialize config.");
        if (Config.generateConfig() && Config.loadConfig()) {
            LOGGER.info("Successfully created config.json or already exist.");
        } else {
            LOGGER.severe("Initializing config failed");
            System.exit(1);
        }

        DiscordClient client = new DiscordClient();
        if (!DiscordConnectionHandling.isConnected()) {
            LOGGER.severe("Could not connect to Discord API.");
            System.exit(1);
        }

    }
}
