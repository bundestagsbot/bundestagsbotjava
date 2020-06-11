package org.bundestagsbot.commands.impl;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import org.bundestagsbot.commands.Command;
import org.bundestagsbot.embeds.NeutralLogEmbed;
import org.bundestagsbot.meta.About;

public class CommandAbout extends ACommandExecutor
{

    @Override
    public void onExecute(Command cmd, JDA jda)
    {
        EmbedBuilder embed = new NeutralLogEmbed();
        embed.setDescription("**About**\n" + About.getInfo());
        cmd.getChannel().sendMessage(embed.build()).queue();
    }

    @Override
    public String getDescription()
    {
        return "Information about this bot";
    }

    @Override
    public String getHelpText()
    {
        return "This gives information about the bot version and other stuff.";
    }
}
