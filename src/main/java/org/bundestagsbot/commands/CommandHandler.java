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
import org.bundestagsbot.config.BotConfigSingleton;
import org.bundestagsbot.embeds.ErrorLogEmbed;
import org.bundestagsbot.exceptions.CommandCreationFailedException;
import org.bundestagsbot.exceptions.CommandExecuteException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandHandler extends ListenerAdapter
{
    private static final Logger logger = LogManager.getLogger(CommandHandler.class.getName());

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

        if (BotConfigSingleton.getInstance().getConfig().getSuggestionChannels().contains(event.getChannel().getId())) {
            return false;
        }

        // TODO: handle per guild config for bot command channel
        return event.getMessage().getType() == MessageType.DEFAULT && (event.getChannelType() == ChannelType.TEXT || event.getChannelType() == ChannelType.PRIVATE);
    }

    public void handle(MessageReceivedEvent event)
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
            if (!ex.getAllowedChannelTypes().isEmpty() && !ex.getAllowedChannelTypes().contains(event.getChannelType())) {
                ErrorLogEmbed errorLogEmbed = new ErrorLogEmbed();
                errorLogEmbed.setDescription("This command is only useable in:\n\n" +
                        ex.getAllowedChannelTypes().stream().map(ChannelType::toString)
                                .collect(Collectors.joining("\n")));
                event.getChannel().sendMessage(errorLogEmbed.build()).queue();
                return;
            }
            try
            {
                logger.info(cmd.getAuthor().getAsTag() + " issued " + cmd.getPrefix() + cmd.getInvoke());
                ex.onExecute(cmd, event.getJDA());
            } catch (Exception exc)
            {
                EmbedBuilder error = new ErrorLogEmbed();
                error.setDescription("**Error**:\nSomething went wrong.");
                event.getChannel().sendMessage(error.build()).queue();
                logger.warn("Failed to execute command \"" + cmd.getPrefix() + cmd.getInvoke() + "\".", exc);
            }
        }
    }
}
