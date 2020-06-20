package org.bundestagsbot.listeners;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bundestagsbot.config.BotConfigSingleton;

import javax.annotation.Nonnull;

public class NewMemberJoined extends ListenerAdapter {

    BotConfigSingleton botConfigSingleton = BotConfigSingleton.getInstance();
    private final String MESSAGE = botConfigSingleton.getConfig().getWelcomeMessage();
    private static final Logger logger = LogManager.getLogger(NewMemberJoined.class.getName());
    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event)
    {
        try {
            event.getGuild().getDefaultChannel().sendMessage(MESSAGE.replace("{username}", event.getUser().getName())).queue();
            logger.info("Sent welcome message for User" + event.getUser().getName());
        }
        catch(NullPointerException e) {
            logger.error("Name of the joining Member or channel wasn't available");
        }
    }
}
