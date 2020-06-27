package org.bundestagsbot.internals.rolesassignment;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.react.GenericGuildMessageReactionEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEmoteEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bundestagsbot.config.BotConfigSingleton;

public class RoleReactions {
    private static final Logger logger = LogManager.getLogger(RoleReactions.class.getName());

    public static void handleReaction(GuildMessageReactionAddEvent event) {
        _handleReaction(event, true);
    }

    public static void handleReaction(GuildMessageReactionRemoveEvent event) {
        _handleReaction(event, false);
    }

    private static void _handleReaction(GenericGuildMessageReactionEvent event, boolean reactionAdded) {
        if (BotConfigSingleton.getInstance().getConfig().getRoleReactionChannels().contains(event.getChannel().getId())) {
            if (BotConfigSingleton.getInstance().getConfig().getRoleOnReaction().containsKey(event.getReactionEmote().getId())) {
                Role selectedRole = event.getGuild().getRoleById(BotConfigSingleton.getInstance().getConfig().getRoleOnReaction().get(event.getReactionEmote().getId()));

                if (event.getMember() == null || selectedRole == null) {
                    logger.warn("Could not fetch member or role on reaction add in channel \"" + event.getChannel().getName() + "\".");
                    return;
                }

                logger.debug("Handling reaction \"" + event.getReactionEmote().getName() + "\" for role \"" + selectedRole.getName() + "\".");
                try {
                    if (reactionAdded) {
                        event.getGuild().addRoleToMember(event.getMember(), selectedRole).queue();
                    } else {
                        event.getGuild().removeRoleFromMember(event.getMember(), selectedRole).queue();
                    }
                } catch(HierarchyException | InsufficientPermissionException | IllegalArgumentException ex) {
                    logger.error("Something went wrong while assigning a role to a user.", ex);
                }

            }
        }
    }
}
