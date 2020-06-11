package org.bundestagsbot.Listeners;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bundestagsbot.Commands.CommandHandler;
import org.bundestagsbot.Exceptions.CommandCreationFailedException;
import org.bundestagsbot.Exceptions.CommandExecuteException;

import javax.annotation.Nonnull;

public class MessageReceived extends ListenerAdapter
{

    private static final Logger logger = LogManager.getLogger(MessageReceived.class.getName());

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event)
    {
        if (event.getAuthor() == event.getJDA().getSelfUser())
            return; // do not log self

        try
        {
            CommandHandler.handle(event);
        } catch (CommandCreationFailedException ignored)
        {
        } catch (CommandExecuteException e)
        {
            e.printStackTrace();
        }
    }
}
