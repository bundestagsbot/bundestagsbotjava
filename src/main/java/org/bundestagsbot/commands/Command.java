package org.bundestagsbot.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.bundestagsbot.config.GuildConfig;
import org.bundestagsbot.exceptions.CommandCreationFailedException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Command
{
    final private User author;
    final private String prefix;
    final private String invoke;
    final private List<String> args;
    final private MessageChannel channel;
    final private Guild guild;
    final private Message message;

    public Command(MessageReceivedEvent event) throws CommandCreationFailedException
    {
        this.author = event.getAuthor();
        this.channel = event.getChannel();
        this.guild = event.getGuild();
        this.message = event.getMessage();

        String messageContent = event.getMessage().getContentRaw();

        this.prefix = (String) GuildConfig.get("command_prefix", "_", this.guild.getId());

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

    public Guild getGuild()
    {
        return guild;
    }

    public Message getMessage()
    {
        return message;
    }
}
