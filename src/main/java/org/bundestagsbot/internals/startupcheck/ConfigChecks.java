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
    private JDA jda;

    public ConfigChecks(JDA jda) {
        this.jda = jda;
    }

    public void checkCompleteConfig() throws ConfigInvalidException {
        countInvalidChannels("suggestion_channels", BotConfigSingleton.getInstance().getConfig().getSuggestionChannels());
        countInvalidChannels("role_reaction_channels", BotConfigSingleton.getInstance().getConfig().getRoleReactionChannels());
        countInvalidEmotes("role_on_reaction", new ArrayList<>(BotConfigSingleton.getInstance().getConfig().getRoleOnReaction().keySet()));
        countInvalidRoles("role_on_reaction", new ArrayList<>(BotConfigSingleton.getInstance().getConfig().getRoleOnReaction().values()));
    }

    private void countInvalidChannels(String configKey, List<String> channelIds) throws ConfigInvalidException {
        for(String channelId : channelIds) {
            try {
                GuildChannel channel = jda.getGuildChannelById(channelId);
                if (channel == null) {
                    throw new ConfigInvalidException("Could not fetch channel \"" + channelId + "\" defined in \"" + configKey + "\".");
                }
            } catch(NumberFormatException ex) {
                throw new ConfigInvalidException("Could not fetch channel \"" + channelId + "\" defined in \"" + configKey + "\".", ex);
            }
        }
    }

    private void countInvalidRoles(String configKey, List<String> roleIds) throws ConfigInvalidException {
        for(String roleId : roleIds ) {
            try {
                Role role = jda.getRoleById(roleId);
                if (role == null) {
                    throw new ConfigInvalidException("Could not fetch role \"" + roleId + "\" defined in \"" + configKey + "\".");
                }
            } catch(NumberFormatException ex) {
                throw new ConfigInvalidException("Could not fetch role \"" + roleId + "\" defined in \"" + configKey + "\".", ex);
            }
        }
    }

    private void countInvalidEmotes(String configKey, List<String> emoteIds) throws ConfigInvalidException {
        for(String emoteId : emoteIds ) {
            try {
                Emote emote = jda.getEmoteById(emoteId);
                if (emote == null) {
                    throw new ConfigInvalidException("Could not fetch emote \"" + emoteId + "\" defined in \"" + configKey + "\".");
                }
            } catch(NumberFormatException ex) {
                throw new ConfigInvalidException("Could not fetch emote \"" + emoteId + "\" defined in \"" + configKey + "\".", ex);
            }
        }
    }
}
