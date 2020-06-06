package org.bundestagsbot.Commands;

import net.dv8tion.jda.api.JDA;
import org.bundestagsbot.Discord.DiscordClient;
import org.bundestagsbot.Exceptions.CommandExecuteException;

import java.util.logging.Logger;

public interface ICommandExecutor {
    Logger LOGGER = Logger.getLogger(DiscordClient.class.getName());
    void onExecute(Command cmd, JDA jda) throws CommandExecuteException;
    String helpString();
}
