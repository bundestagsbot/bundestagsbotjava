package org.bundestagsbot.listeners;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bundestagsbot.config.BotConfigSingleton;

import javax.annotation.Nonnull;

public class GuildMemberJoin extends ListenerAdapter {

    BotConfigSingleton botConfigSingleton = BotConfigSingleton.getInstance();
    private static final Logger logger = LogManager.getLogger(GuildMemberJoin.class.getName());
    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event)
    {
        String message = botConfigSingleton.getConfig().getWelcomeMessage().replace("{username}", event.getUser().getName());
        try {
            event.getUser().openPrivateChannel().queue(channel -> channel.sendMessage(message).queue());
            logger.info("Sent welcome message to user " + event.getUser().getAsTag());
        }
        catch(Exception e) {
            logger.error("Couldn't send welcome message: ", e);
        }
    }
}
