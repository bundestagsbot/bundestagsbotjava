package org.bundestagsbot.commands;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.bundestagsbot.config.GuildConfig;
import org.bundestagsbot.exceptions.CommandCreationFailedException;

import javax.annotation.Nullable;
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
    final private Message message;
    @Nullable
    final private Guild guild;

    public Command(MessageReceivedEvent event) throws CommandCreationFailedException
    {
        this.author = event.getAuthor();
        this.message = event.getMessage();
        this.channel = event.getChannel();

        if (event.getChannelType() == ChannelType.GROUP || event.getChannelType() == ChannelType.PRIVATE) {
            this.guild = null;
            this.prefix = "_";  // TODO: use default command prefix from global config
        } else {
            this.guild = event.getGuild();
            this.prefix = (String) GuildConfig.get("command_prefix", "_", this.guild.getId());
        }

        String messageContent = event.getMessage().getContentRaw();
        if (messageContent.isEmpty() || !messageContent.startsWith(prefix) || messageContent.equals(prefix))
        {
            throw new CommandCreationFailedException("No valid command invoked.");
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

    @Nullable
    public Guild getGuild()
    {
        return guild;
    }

    public Message getMessage()
    {
        return message;
    }
}
