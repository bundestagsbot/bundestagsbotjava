package org.bundestagsbot.commands;

import net.dv8tion.jda.api.JDA;
import org.bundestagsbot.exceptions.CommandExecuteException;

public interface ICommandExecutor
{
    void onExecute(Command cmd, JDA jda) throws CommandExecuteException;

    String helpString();
}
