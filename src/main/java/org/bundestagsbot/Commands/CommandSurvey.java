package org.bundestagsbot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import org.apache.commons.lang.StringUtils;
import org.bundestagsbot.Embeds.ErrorLogEmbed;
import org.bundestagsbot.Embeds.NeutralLogEmbed;
import org.bundestagsbot.Embeds.WarningLogEmbed;
import org.bundestagsbot.Exceptions.CommandExecuteException;
import org.bundestagsbot.Internals.Surveys.DawumJsonMapper.Parliament;
import org.bundestagsbot.Internals.Surveys.DawumJsonMapper.RootDawumJson;
import org.bundestagsbot.Internals.Surveys.DawumJsonMapper.Survey;
import org.bundestagsbot.Internals.Surveys.SurveyRequester;

import java.io.IOException;
import java.util.*;
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
        String parliamentId = "0";  // defaults to bundestag
        if (cmd.getArgs().size() > 1)
        {
            if (StringUtils.isNumeric(cmd.getArgs().get(1)))
            {
                parliamentId = cmd.getArgs().get(1);
            }
        }
        if (!response.getParliamentMap().containsKey(parliamentId))
        {
            // if parliament id is not in api response then something is very wrong
            if (parliamentId.equals("0"))
            {
                LOGGER.severe("Got an invalid syntax. Perhaps the API is not available or deprecated.");
                EmbedBuilder error = new ErrorLogEmbed();
                error.setDescription("**Error**\nGot an invalid syntax. Perhaps the API is not available or deprecated.");
                cmd.getChannel().sendMessage(error.build()).queue();
                return;
            }
            parliamentId = "0";  // defaulting to bundestag
        }

        EmbedBuilder info = new NeutralLogEmbed();

        ArrayList<String> surveyKeys = new ArrayList<String>(response.getSurveyMap().keySet());
        // https://stackoverflow.com/a/13973660/9850709
        surveyKeys.sort(new Comparator<String>() {
            public int compare(String o1, String o2) {
                return extractInt(o2) - extractInt(o1);
            }

            int extractInt(String s) {
                String num = s.replaceAll("\\D", "");
                // return 0 if no digits found
                return num.isEmpty() ? 0 : Integer.parseInt(num);
            }
        });

        for(String surveyKey: surveyKeys)
        {
            if (response.getSurvey(surveyKey).getParliamentId().equals(parliamentId))
            {
                Survey matchedSurvey = response.getSurvey(surveyKey);
                info.setDescription("**Election**: " + response.getParliament(parliamentId).getElection() + "\n" +
                        "**Survey by**: " + response.getInstitute(matchedSurvey.getInstituteId()).getName() + "\n" +
                        "**Survey issued by**: " + response.getTasker(matchedSurvey.getTaskerId()).getName());
                info.setFooter("Survey date: " + matchedSurvey.getDate());

                for (Map.Entry<String, Integer> entry : matchedSurvey.getResults().entrySet())
                {
                    info.addField(response.getParty(entry.getKey()).getName(), entry.getValue().toString() + "%", false);
                }
                break;
            }
        }
        cmd.getChannel().sendMessage(info.build()).queue();
    }

    @Override
    public String helpString() {
        return "This returns the newest survey | \"Sonntagsfrage\".";
    }
}
