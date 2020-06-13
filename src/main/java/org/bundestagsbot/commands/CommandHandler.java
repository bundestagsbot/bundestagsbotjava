package org.bundestagsbot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.MessageType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bundestagsbot.commands.impl.ACommandExecutor;
import org.bundestagsbot.commands.impl.ECommandExecutor;
import org.bundestagsbot.commands.impl.ICommandExecutor;
import org.bundestagsbot.embeds.ErrorLogEmbed;
import org.bundestagsbot.exceptions.CommandCreationFailedException;
import org.bundestagsbot.exceptions.CommandExecuteException;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler extends ListenerAdapter
{
    private static final Logger logger = LogManager.getLogger(CommandHandler.class.getName());
    // register commands here

    private final Map<String, ICommandExecutor> commands = new HashMap<>();

    public Map<String, ICommandExecutor> getCommands()
    {
        return commands;
    }

    public CommandHandler()
    {
        for (ECommandExecutor executor : ECommandExecutor.values())
        {
            try
            {
                ACommandExecutor cmdEx = (ACommandExecutor) executor.getInstanceableClass().newDefaultInstance();
                cmdEx.setParent(this);
                commands.put(executor.toString().toLowerCase(), cmdEx);
            } catch (Exception e)
            {
                logger.error("Could not create instance of Task: " + executor.toString());
            }
        }
    }

    private boolean validateMessage(MessageReceivedEvent event)
    {
        if (event.getAuthor() == event.getJDA().getSelfUser())
            return false;

        // TODO: handle per guild config for bot command channel
        return event.getMessage().getType() == MessageType.DEFAULT && (event.getChannelType() == ChannelType.TEXT || event.getChannelType() == ChannelType.PRIVATE);
    }

    public void handle(MessageReceivedEvent event) throws CommandExecuteException
    {
        if (!validateMessage(event))
        {
            return;
        }

        Command cmd;
        try
        {
            cmd = new Command(event);
        } catch (CommandCreationFailedException ex)
        {
            return;
        }
        if (commands.containsKey(cmd.getInvoke()))
        {
            ICommandExecutor ex = commands.get(cmd.getInvoke());
            if (!ex.userHasPermission(cmd, event.getJDA())) {
                ErrorLogEmbed errorLogEmbed = new ErrorLogEmbed();
                errorLogEmbed.setDescription("You do not have the needed permission for this command.");
                event.getChannel().sendMessage(errorLogEmbed.build()).queue();
                return;
            }
            try
            {
                logger.info(cmd.getAuthor().getAsTag() + " issued " + cmd.getPrefix() + cmd.getInvoke());
                ex.onExecute(cmd, event.getJDA());
            } catch (CommandExecuteException cmdEx)
            {
                logger.warn("Failed to execute command \"" + cmd.getPrefix() + cmd.getInvoke() + "\", error got handled:");
                cmdEx.printStackTrace();
            } catch (Exception defaultEx)
            {
                EmbedBuilder error = new ErrorLogEmbed();
                error.setDescription("**Error**:\nSomething went wrong.");
                event.getChannel().sendMessage(error.build()).queue();
                throw new CommandExecuteException("Failed to execute command " + cmd.getInvoke(), defaultEx);
            }
        }
    }
}
