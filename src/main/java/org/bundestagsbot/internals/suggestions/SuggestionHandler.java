package org.bundestagsbot.internals.suggestions;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bundestagsbot.commands.CommandHandler;
import org.bundestagsbot.config.BotConfigSingleton;
import org.bundestagsbot.embeds.NeutralLogEmbed;

public class SuggestionHandler {
    private static final Logger logger = LogManager.getLogger(CommandHandler.class.getName());

    public void handle(MessageReceivedEvent event) {
        if (ChannelType.PRIVATE.equals(event.getChannelType())) {
            return;
        }
        if (!BotConfigSingleton.getInstance().getConfig().getSuggestionChannels().contains(event.getChannel().getId())) {
            return;
        }

        logger.info("Handle suggestion by " + event.getAuthor().getAsTag());

        NeutralLogEmbed suggestionEmbed = new NeutralLogEmbed();
        suggestionEmbed.setAuthor(event.getAuthor().getName(), event.getAuthor().getEffectiveAvatarUrl(), event.getAuthor().getEffectiveAvatarUrl());
        suggestionEmbed.setFooter("UserId: " + event.getAuthor().getId());
        String messageContent = event.getMessage().getContentStripped();
        suggestionEmbed.setDescription(messageContent.substring(0, Math.min(messageContent.length(), 1999)));
        suggestionEmbed.setTitle("Suggestion by " + event.getAuthor().getName());

        event.getMessage().delete().queue();
        event.getChannel().sendMessage(suggestionEmbed.build()).queue(success -> {
            success.addReaction("\uD83D\uDC4D").queue();  // thumbs up
            success.addReaction("\uD83D\uDC4E").queue();  // thumbs down
        });
    }
}
