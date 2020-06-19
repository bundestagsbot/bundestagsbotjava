package org.bundestagsbot.commands;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.bundestagsbot.config.BotConfigSingleton;
import org.bundestagsbot.exceptions.CommandCreationFailedException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Command
{
    final private User author;
    final private String prefix;
    final private String invoke;
    final private List<String> args;
    final private MessageChannel channel;
    final private Message message;
    final private Guild guild;

    public Command(MessageReceivedEvent event) throws CommandCreationFailedException
    {
        this.author = event.getAuthor();
        this.channel = event.getChannel();
        this.message = event.getMessage();
        this.prefix = BotConfigSingleton.getInstance().getConfig().getCommandPrefix();

        if (!ChannelType.PRIVATE.equals(event.getChannelType())) {
            this.guild = event.getGuild();
        } else {
            this.guild = null;
        }

        String messageContent = event.getMessage().getContentRaw();


        if (messageContent.isEmpty() || !messageContent.startsWith(prefix) || messageContent.equals(prefix))
        {
            this.args = Collections.emptyList();
            this.invoke = "";
            return;
        }

        this.args = Arrays.asList(messageContent.split(" +"));
        try
        {
            this.invoke = args.get(0).substring(this.prefix.length());
        } catch (IndexOutOfBoundsException ex)
        {
            throw new CommandCreationFailedException("Command creation failed", ex);
        }
    }

    public User getAuthor()
    {
        return author;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public String getInvoke()
    {
        return invoke;
    }

    public List<String> getArgs()
    {
        return args;
    }

    public MessageChannel getChannel()
    {
        return channel;
    }

    public Optional<Guild> getGuild()
    {
        return Optional.ofNullable(guild);
    }

    public Message getMessage()
    {
        return message;
    }
}
