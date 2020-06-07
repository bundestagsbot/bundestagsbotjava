package org.bundestagsbot.Discord;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bundestagsbot.Config.Config;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bundestagsbot.Listeners.MessageReceived;

import javax.security.auth.login.LoginException;
import java.util.Collection;

public class DiscordClient extends ListenerAdapter {

    private static final Logger logger = LogManager.getLogger(DiscordClient.class.getName());

    public boolean isConnected() {
        return connected;
    }

    private boolean connected;

    public DiscordClient() {
        // Initialize
        String token = (String) Config.get("discord_bot_token");
        if ("bot_token_goes_here".equals(token)) {
            logger.error("Please update your config.json");
            System.exit(1);
        }
        logger.info("Connecting bot...");

        try {
            JDA jda = JDABuilder.create(token, GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS))
                    .addEventListeners(new DiscordConnectionHandling())
                    .addEventListeners(new MessageReceived())
                    .build();
            jda.awaitReady();
        } catch(LoginException | InterruptedException ex) {
            logger.error("Failed to login to Discord.");
        }
    }

}
