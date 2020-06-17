package org.bundestagsbot.discord;

import net.dv8tion.jda.api.entities.Guild;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.DisconnectEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ReconnectedEvent;
import net.dv8tion.jda.api.events.ResumedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bundestagsbot.config.global.GlobalConfigSingleton;
import org.bundestagsbot.config.guild.GuildConfigSingleton;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

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
        Object activity = GlobalConfigSingleton.getInstance().getConfig().getActivityString();
        if (activity != null) {
            event.getJDA().getPresence().setActivity(Activity.playing(activity.toString()));
        }
        logger.debug("Loading guild configs for already joined guilds.");
        List<String> knownGuildIds = event.getJDA().getGuilds().stream().map(Guild::getId).collect(Collectors.toList());
        GuildConfigSingleton.configureInstance(knownGuildIds);
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
