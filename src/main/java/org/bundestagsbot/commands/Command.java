package org.bundestagsbot.commands;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.bundestagsbot.config.GuildConfig;
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
    final private Optional<Guild> guild;

    public Command(MessageReceivedEvent event) throws CommandCreationFailedException
    {
        this.author = event.getAuthor();
        this.channel = event.getChannel();
        this.message = event.getMessage();

        if (!ChannelType.PRIVATE.equals(event.getChannelType())) {
            this.guild = Optional.of(event.getGuild());
            this.prefix = (String) GuildConfig.get("command_prefix", "_", this.guild.get().getId());
        } else {
            this.guild = Optional.empty();
            this.prefix = "_";  // TODO: default prefix from global config
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
        return guild;
    }

    public Message getMessage()
    {
        return message;
    }
}
