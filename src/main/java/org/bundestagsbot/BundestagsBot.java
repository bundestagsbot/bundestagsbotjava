package org.bundestagsbot;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bundestagsbot.config.GlobalConfig;
import org.bundestagsbot.discord.DiscordClient;
import org.bundestagsbot.discord.DiscordConnectionHandling;
import org.bundestagsbot.meta.About;

public class BundestagsBot
{
    private static final Logger logger = LogManager.getLogger(BundestagsBot.class.getName());

    public static void main(String[] args)
    {
        logger.info("Starting up.");

        // Here you could register an log observer, this is very useful, e.g.
        // for forwarding special log levels to... special logs, GUI, discord, etc.
        // LogRouter.getInstance().addObserver(gui);

        logger.info(About.getInfo());

        logger.debug("Initialize config.");
        if (GlobalConfig.generateConfig() && GlobalConfig.loadConfig())
        {
            logger.info("Successfully created config.json or already exist.");
        } else
        {
            logger.error("Initializing config failed");
            System.exit(1);
        }

        DiscordClient client = new DiscordClient();
        if (!DiscordConnectionHandling.isConnected())
        {
            logger.error("Could not connect to Discord API.");
            System.exit(1);
        }

    }
}
