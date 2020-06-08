package org.bundestagsbot.Discord;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bundestagsbot.Config.GlobalConfig;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.DisconnectEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ReconnectedEvent;
import net.dv8tion.jda.api.events.ResumedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bundestagsbot.Listeners.MessageReceived;

import javax.annotation.Nonnull;

public class DiscordConnectionHandling extends ListenerAdapter {

    private static boolean connected;
    private static final Logger logger = LogManager.getLogger(DiscordConnectionHandling.class.getName());

    public static boolean isConnected() {
        return connected;
    }

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        logger.info("Bot connected to Discord API.");
        connected = true;
        Object activity = GlobalConfig.get("activity_string");
        if (activity != null) {
            event.getJDA().getPresence().setActivity(Activity.playing(activity.toString()));
        }
    }

    @Override
    public void onReconnect(@Nonnull ReconnectedEvent event) {
        logger.info("Bot reconnected to Discord API.");
        connected = true;
    }

    @Override
    public void onResume(@Nonnull ResumedEvent event) {
        logger.info("Bot reconnected to Discord API.");
        connected = true;
    }

    @Override
    public void onDisconnect(@Nonnull DisconnectEvent event) {
        logger.error("Bot disconnected to Discord API.");
        connected = false;
    }
}
