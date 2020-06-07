package org.bundestagsbot.Commands;

import net.dv8tion.jda.api.JDA;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bundestagsbot.Config.Config;
import org.bundestagsbot.Discord.DiscordClient;
import org.bundestagsbot.Exceptions.CommandExecuteException;

public interface ICommandExecutor {
    void onExecute(Command cmd, JDA jda) throws CommandExecuteException;
    String helpString();
}
