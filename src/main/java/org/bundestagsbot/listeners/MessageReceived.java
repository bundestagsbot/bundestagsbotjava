package org.bundestagsbot.listeners;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bundestagsbot.commands.Command;
import org.bundestagsbot.commands.CommandHandler;
import org.bundestagsbot.exceptions.CommandExecuteException;

import javax.annotation.Nonnull;

public class MessageReceived extends ListenerAdapter
{

    private static final Logger logger = LogManager.getLogger(MessageReceived.class.getName());

    private final CommandHandler handler = new CommandHandler();


    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event)
    {
        if (event.getAuthor() == event.getJDA().getSelfUser())
            return; // do not log self

        handler.handle(event);
    }
}
