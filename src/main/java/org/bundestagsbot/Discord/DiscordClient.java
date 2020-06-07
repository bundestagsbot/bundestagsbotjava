package org.bundestagsbot.Discord;

import org.bundestagsbot.Config.GlobalConfig;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bundestagsbot.Listeners.MessageReceived;

import javax.security.auth.login.LoginException;
import java.util.logging.Logger;

public class DiscordClient extends ListenerAdapter {

    private final static Logger LOGGER = Logger.getLogger(DiscordClient.class.getName());

    public boolean isConnected() {
        return connected;
    }

    private boolean connected;

    public DiscordClient() {
        // Initialize
        String token = (String) GlobalConfig.get("discord_bot_token");
        if (token.equals("bot_token_goes_here")) {
            LOGGER.severe("Please update your config.json");
            System.exit(1);
        }
        LOGGER.info("Connecting bot...");

        try {
            JDA jda = JDABuilder.create(token, GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS))
                    .addEventListeners(new DiscordConnectionHandling())
                    .addEventListeners(new MessageReceived())
                    .build();
            jda.awaitReady();
        } catch(LoginException | InterruptedException ex) {
            LOGGER.severe("Failed to login to Discord.");
        }
    }

}
