package org.bundestagsbot.Commands;

import net.dv8tion.jda.api.JDA;
import org.bundestagsbot.Exceptions.CommandExecuteException;

public interface ICommandExecutor
{
    void onExecute(Command cmd, JDA jda) throws CommandExecuteException;

    String helpString();
}
