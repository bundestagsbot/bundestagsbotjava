package org.bundestagsbot.internals.startupcheck;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Role;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bundestagsbot.config.BotConfigSingleton;
import org.bundestagsbot.exceptions.ConfigInvalidException;

import java.util.ArrayList;
import java.util.List;

public class ConfigChecks {
    private static final Logger logger = LogManager.getLogger(ConfigChecks.class.getName());
    private int allowedErrors = -1;
    private int occurredErrors = 0;
    private JDA jda;

    public ConfigChecks(JDA jda) {
        this.jda = jda;
    }

    public ConfigChecks(JDA jda, int allowedErrors) {
        this.jda = jda;
        this.allowedErrors = allowedErrors;
    }

    public void checkConfig() throws ConfigInvalidException {
        occurredErrors += countInvalidChannels("suggestion_channels", BotConfigSingleton.getInstance().getConfig().getSuggestionChannels());
        occurredErrors += countInvalidChannels("role_reaction_channels", BotConfigSingleton.getInstance().getConfig().getRoleReactionChannels());
        occurredErrors += countInvalidEmotes("role_on_reaction", new ArrayList<>(BotConfigSingleton.getInstance().getConfig().getRoleOnReaction().keySet()));
        occurredErrors += countInvalidRoles("role_on_reaction", new ArrayList<>(BotConfigSingleton.getInstance().getConfig().getRoleOnReaction().values()));

        if (allowedErrors != -1 && occurredErrors > allowedErrors) {
            throw new ConfigInvalidException("There were too many errors while checking the config.");
        }
    }

    private int countInvalidChannels(String configKey, List<String> channelIds) {
        int count = 0;
        for(String channelId : channelIds) {
            try {
                GuildChannel channel = jda.getGuildChannelById(channelId);
                if (channel == null) {
                    logger.warn("Could not fetch channel \"" + channelId + "\" defined in \"" + configKey + "\".");
                    count++;
                }
            } catch(NumberFormatException ex) {
                logger.warn("Could not fetch channel \"" + channelId + "\" defined in \"" + configKey + "\".", ex);
                count++;
            }
        }
        return count;
    }

    private int countInvalidRoles(String configKey, List<String> roleIds) {
        int count = 0;
        for(String roleId : roleIds ) {
            try {
                Role role = jda.getRoleById(roleId);
                if (role == null) {
                    logger.warn("Could not fetch role \"" + roleId + "\" defined in \"" + configKey + "\".");
                    count++;
                }
            } catch(NumberFormatException ex) {
                logger.warn("Could not fetch role \"" + roleId + "\" defined in \"" + configKey + "\".", ex);
                count++;
            }
        }
        return count;
    }

    private int countInvalidEmotes(String configKey, List<String> emoteIds) {
        int count = 0;
        for(String emoteId : emoteIds ) {
            try {
                Emote emote = jda.getEmoteById(emoteId);
                if (emote == null) {
                    logger.warn("Could not fetch emote \"" + emoteId + "\" defined in \"" + configKey + "\".");
                    count++;
                }
            } catch(NumberFormatException ex) {
                logger.warn("Could not fetch emote \"" + emoteId + "\" defined in \"" + configKey + "\".", ex);
                count++;

            }
        }
        return count;
    }

    public int getAllowedErrors() {
        return allowedErrors;
    }

    public int getOccurredErrors() {
        return occurredErrors;
    }
}
