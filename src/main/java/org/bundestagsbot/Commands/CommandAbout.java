package org.bundestagsbot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import org.bundestagsbot.Embeds.NeutralLogEmbed;
import org.bundestagsbot.Meta.About;

public class CommandAbout implements ICommandExecutor{
    @Override
    public void onExecute(Command cmd, JDA jda) {
        EmbedBuilder embed = new NeutralLogEmbed();
        embed.setDescription("**About**\n" + About.getInfo());
        cmd.getChannel().sendMessage(embed.build()).queue();
    }

    @Override
    public String helpString() {
        return "This gives information about the bot version and other stuff.";
    }
}
