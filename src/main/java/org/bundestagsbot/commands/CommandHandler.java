package org.bundestagsbot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.MessageType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bundestagsbot.commands.impl.CommandAbout;
import org.bundestagsbot.commands.impl.CommandHelp;
import org.bundestagsbot.commands.impl.CommandSurvey;
import org.bundestagsbot.embeds.ErrorLogEmbed;
import org.bundestagsbot.exceptions.CommandCreationFailedException;
import org.bundestagsbot.exceptions.CommandExecuteException;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler extends ListenerAdapter
{
    private static final Logger logger = LogManager.getLogger(CommandHandler.class.getName());
    // register commands here
    private static final Map<String, ICommandExecutor> commands = new HashMap<>()
    {{
        put("about", new CommandAbout());
        put("survey", new CommandSurvey());
        put("help", new CommandHelp());
    }};

    public static Map<String, ICommandExecutor> getCommands()
    {
        return commands;
    }

    private static boolean validateMessage(MessageReceivedEvent event)
    {
        if (event.getAuthor() == event.getJDA().getSelfUser())
            return false;

        // TODO: handle per guild config for bot command channel

        return event.getMessage().getType() == MessageType.DEFAULT && (event.getChannelType() == ChannelType.TEXT || event.getChannelType() == ChannelType.PRIVATE);
    }

    public static void handle(MessageReceivedEvent event) throws CommandExecuteException
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