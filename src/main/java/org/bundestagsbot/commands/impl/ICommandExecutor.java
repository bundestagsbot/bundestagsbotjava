package org.bundestagsbot.commands.impl;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.ChannelType;
import org.bundestagsbot.commands.Command;
import org.bundestagsbot.exceptions.CommandExecuteException;

import java.util.List;

public interface ICommandExecutor
{
    void onExecute(Command cmd, JDA jda) throws CommandExecuteException;

    String getDescription();

    String getHelpText();

    List<ChannelType> getAllowedChannelTypes();
}
