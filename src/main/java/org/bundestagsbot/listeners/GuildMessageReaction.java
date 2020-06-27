package org.bundestagsbot.listeners;

import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bundestagsbot.internals.rolesassignment.RoleReactions;

import javax.annotation.Nonnull;

public class GuildMessageReaction extends ListenerAdapter {
    private static final Logger logger = LogManager.getLogger(GuildMessageReaction.class.getName());

    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent event) {
        RoleReactions.handleReaction(event);
    }

    @Override
    public void onGuildMessageReactionRemove(@Nonnull GuildMessageReactionRemoveEvent event) {
        RoleReactions.handleReaction(event);
    }
}
