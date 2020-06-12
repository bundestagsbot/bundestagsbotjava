package org.bundestagsbot.commands.impl;

import net.dv8tion.jda.api.JDA;
import org.bundestagsbot.commands.Command;
import org.bundestagsbot.exceptions.CommandExecuteException;

public interface ICommandExecutor
{
    void onExecute(Command cmd, JDA jda) throws CommandExecuteException;

    String getDescription();

    String getHelpText();
}
