package org.bundestagsbot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import org.bundestagsbot.Embeds.ErrorLogEmbed;
import org.bundestagsbot.Embeds.NeutralLogEmbed;
import org.bundestagsbot.Exceptions.CommandExecuteException;

import javax.annotation.Nullable;
import java.util.HashMap;

public class CommandHelp implements ICommandExecutor{
    @Override
    public void onExecute(Command cmd, JDA jda) throws CommandExecuteException {
        EmbedBuilder info = new NeutralLogEmbed();
        if (cmd.getArgs().size() > 1) {
            // test if there exists a help page for this command, if not print error
            String helpForCmd = cmd.getArgs().get(1);
            String tempHelp = getHelpByCommandInvoke(helpForCmd);
            if (tempHelp != null && !tempHelp.isEmpty()) {
                info.setDescription("**Help - " + helpForCmd + "**\n" + tempHelp);
            } else
            {
                EmbedBuilder error = new ErrorLogEmbed();
                error.setDescription("**Help**\nThere is no help page for this command.");
                cmd.getChannel().sendMessage(error.build()).queue();
                return;
            }
        } else {
            info.setDescription("**Help - Help**\n" + helpString());
        }
        cmd.getChannel().sendMessage(info.build()).queue();
    }

    /**
     * This function gets the help string of a defined command
     * @param cmd Define the command to search for (searching stripped and lowercase)
     * @return Help string or null if not found
     */
    @Nullable
    private String getHelpByCommandInvoke(String cmd)
    {
        HashMap<String, ICommandExecutor> commands = CommandHandler.getCommands();
        return commands.get(cmd.strip().toLowerCase()) != null ? commands.get(cmd.strip().toLowerCase()).helpString() : null;
    }

    @Override
    public String helpString() {
        return "Usage:\n" +
               "help <cmd>\n\n" +
               "Prints help and usage page for a defined command.";
    }
}
