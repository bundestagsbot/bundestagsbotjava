package org.bundestagsbot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bundestagsbot.Embeds.ErrorLogEmbed;
import org.bundestagsbot.Exceptions.CommandCreationFailedException;
import org.bundestagsbot.Exceptions.CommandExecuteException;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class CommandHandler extends ListenerAdapter {
    private final static Logger LOGGER = Logger.getLogger(CommandHandler.class.getName());
    // register commands here
    private static final HashMap<String, ICommandExecutor> commands = new HashMap<String, ICommandExecutor>() {{
        put("about", new CommandAbout());
        put("survey", new CommandSurvey());
        put("help", new CommandHelp());
    }};

    public static HashMap<String, ICommandExecutor> getCommands() { return commands; }

    private static boolean validateMessage(MessageReceivedEvent event)
    {
        if (event.getAuthor() == event.getJDA().getSelfUser())
            return false;

        // TODO: handle per guild config for bot command channel

        if (event.getMessage().getType() != MessageType.DEFAULT || (event.getChannelType() != ChannelType.TEXT && event.getChannelType() != ChannelType.PRIVATE)) {
            return false;
        }

        return true;
    }

    public static void handle(MessageReceivedEvent event) throws CommandCreationFailedException, CommandExecuteException {
        if (!validateMessage(event)) {
            return;
        }

        Command cmd;
        try {
            cmd = new Command(event);
        }
        catch (CommandCreationFailedException ex) {
            return;
        }
        if (commands.containsKey(cmd.getInvoke())) {
            ICommandExecutor ex = commands.get(cmd.getInvoke());
            try {
                LOGGER.info(cmd.getAuthor().getAsTag() + " issued " + cmd.getPrefix() + cmd.getInvoke());
                ex.onExecute(cmd, event.getJDA());
            } catch (CommandExecuteException cmdEx)
            {
                LOGGER.warning("Failed to execute command \"" + cmd.getPrefix() + cmd.getInvoke() + "\", error got handled:");
                cmdEx.printStackTrace();
            } catch (Exception defaultEx) {
                EmbedBuilder error = new ErrorLogEmbed();
                error.setDescription("**Error**:\nSomething went wrong.");
                event.getChannel().sendMessage(error.build()).queue();
                throw new CommandExecuteException("Failed to execute command " + cmd.getInvoke(), defaultEx);
            }
        }
    }
}
