package org.bundestagsbot.commands.impl;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import org.bundestagsbot.commands.Command;
import org.bundestagsbot.embeds.ErrorLogEmbed;
import org.bundestagsbot.embeds.NeutralLogEmbed;

import javax.annotation.Nullable;
import java.util.Map;

public class CommandHelp extends ACommandExecutor
{
    @Override
    public void onExecute(Command cmd, JDA jda)
    {
        EmbedBuilder info = new NeutralLogEmbed();
        if (cmd.getArgs().size() > 1)
        {
            // test if there exists a help page for this command, if not print error
            String helpForCmd = cmd.getArgs().get(1);
            String tempHelp = getHelpByCommandInvoke(helpForCmd);
            if (tempHelp != null && !tempHelp.isEmpty())
            {
                info.setDescription("**Help - " + helpForCmd + "**\n" + tempHelp);
            } else
            {
                EmbedBuilder error = new ErrorLogEmbed();
                error.setDescription("**Help**\nThere is no help page for this command.");
                cmd.getChannel().sendMessage(error.build()).queue();
                return;
            }
        } else
        {
            info.setDescription("**Help - Help**\n" + getHelpText());
        }
        cmd.getChannel().sendMessage(info.build()).queue();
    }

    /**
     * This function gets the help string of a defined command
     *
     * @param cmd Define the command to search for (searching stripped and lowercase)
     * @return Help string or null if not found
     */
    @Nullable
    private String getHelpByCommandInvoke(String cmd)
    {
        if (getParent().getCommands().containsKey(cmd.strip().toLowerCase()))
        {
            ICommandExecutor executor = getParent().getCommands().get(cmd.strip().toLowerCase());
            return executor.getHelpText();
        }
        return "";
    }

    @Override
    public String getHelpText()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Usage:\n" +
                "help <cmd>\n\n" +
                "Prints help and usage page for a defined command.\n\n" +
                "Available commands: \n");
        for (Map.Entry<String, ICommandExecutor> executor : getParent().getCommands().entrySet())
        {
            builder.append("**")
                    .append(executor.getKey())
                    .append("**")
                    .append(": ")
                    .append(executor.getValue().getDescription())
                    .append("\n");
        }
        return builder.toString();
    }

    @Override
    public String getDescription()
    {
        return "Prints this help text";
    }
}
