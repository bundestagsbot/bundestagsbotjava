package org.bundestagsbot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import org.bundestagsbot.Embeds.ErrorLogEmbed;
import org.bundestagsbot.Embeds.NeutralLogEmbed;
import org.bundestagsbot.Exceptions.CommandExecuteException;
import org.bundestagsbot.Internals.Surveys.DawumJsonMapper.Parliament;
import org.bundestagsbot.Internals.Surveys.DawumJsonMapper.RootDawumJson;
import org.bundestagsbot.Internals.Surveys.SurveyRequester;

import java.io.IOException;
import java.util.stream.Collectors;

public class CommandSurvey implements ICommandExecutor{
    @Override
    public void onExecute(Command cmd, JDA jda) throws CommandExecuteException {
        RootDawumJson response;
        try {
            response = SurveyRequester.getAPIResponse();
        } catch (IOException e) {
            EmbedBuilder error = new ErrorLogEmbed();
            error.setDescription("**Error**:\nCannot fetch from Dawum API.\nPlease try again later.");
            cmd.getChannel().sendMessage(error.build()).queue();
            throw new CommandExecuteException("Cannot fetch from Dawum API.\nPlease try again later.", e);
        }
        EmbedBuilder info = new NeutralLogEmbed();
        //info.setDescription(response.getDatabase().getAuthor());
        info.setDescription(response.getParliaments() .stream().map(Parliament::getName).collect(Collectors.joining("\n")));
        cmd.getChannel().sendMessage(info.build()).queue();
    }

    @Override
    public String helpString() {
        return "This returns the newest survey | \"Sonntagsfrage\".";
    }
}
