package org.bundestagsbot.Listeners;

import org.bundestagsbot.Commands.CommandHandler;
import org.bundestagsbot.Config.Config;
import org.bundestagsbot.Discord.DiscordClient;
import org.bundestagsbot.Exceptions.CommandCreationFailedException;
import org.bundestagsbot.Exceptions.CommandExecuteException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.logging.Logger;

public class MessageReceived extends ListenerAdapter {

    private final static Logger LOGGER = Logger.getLogger(DiscordClient.class.getName());

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (event.getAuthor() == event.getJDA().getSelfUser())
            return; // do not log self

        try {
            CommandHandler.handle(event);
        } catch (CommandCreationFailedException ignored) {
        } catch (CommandExecuteException e) {
            e.printStackTrace();
        }
    }
}
