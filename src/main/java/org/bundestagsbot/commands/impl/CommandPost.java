package org.bundestagsbot.commands.impl;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bundestagsbot.commands.Command;
import org.bundestagsbot.embeds.ErrorLogEmbed;
import org.bundestagsbot.embeds.NeutralLogEmbed;
import org.bundestagsbot.exceptions.CommandExecuteException;

public class CommandPost extends ACommandExecutor{
    @Override
    public void onExecute(Command cmd, JDA jda) throws CommandExecuteException {
        if (cmd.getArgs().size() < 3) {
            ErrorLogEmbed errorLogEmbed = new ErrorLogEmbed();
            errorLogEmbed.setDescription("Invalid number of arguments, please refer to: `help post`");
            cmd.getChannel().sendMessage(errorLogEmbed.build()).queue();
            return;
        }

        if (cmd.getMessage().getMentionedChannels().isEmpty()) {
            ErrorLogEmbed errorLogEmbed = new ErrorLogEmbed();
            errorLogEmbed.setDescription("Please mention a channel to post to.");
            cmd.getChannel().sendMessage(errorLogEmbed.build()).queue();
            return;
        }

        TextChannel channelToPost = cmd.getMessage().getMentionedChannels().get(0);
        String messageToPost = String.join(" ", cmd.getArgs().subList(2, cmd.getArgs().size()));
        NeutralLogEmbed embedToPost = new NeutralLogEmbed();
        embedToPost.setDescription(messageToPost);

        channelToPost.sendMessage(embedToPost.build()).queue();
    }

    @Override
    public String getDescription() {
        return "Post an anonymous embed with a defined text in a specified channel.";
    }

    @Override
    public String getHelpText() {
        return "Usage:\n" +
                "post <#channel> <text>\n\n" +
                "Post an embed in a specified channel.\n" +
                "Bot needs to have write access";
    }
}
