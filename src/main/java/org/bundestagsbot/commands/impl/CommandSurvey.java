package org.bundestagsbot.commands.impl;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bundestagsbot.commands.Command;
import org.bundestagsbot.embeds.ErrorLogEmbed;
import org.bundestagsbot.embeds.NeutralLogEmbed;
import org.bundestagsbot.exceptions.CommandExecuteException;
import org.bundestagsbot.internals.surveys.SurveyRequester;
import org.bundestagsbot.internals.surveys.dawumjsonmapper.RootDawumJson;
import org.bundestagsbot.internals.surveys.dawumjsonmapper.Survey;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class CommandSurvey extends ACommandExecutor
{

    private static final Logger logger = LogManager.getLogger(CommandSurvey.class.getName());

    @Override
    public void onExecute(Command cmd, JDA jda) throws CommandExecuteException
    {
        RootDawumJson response;
        try
        {
            response = SurveyRequester.getAPIResponse();
        } catch (IOException e)
        {
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
                logger.error("Got an invalid syntax. Perhaps the API is not available or deprecated.");
                EmbedBuilder error = new ErrorLogEmbed();
                error.setDescription("**Error**\nGot an invalid syntax. Perhaps the API is not available or deprecated.");
                cmd.getChannel().sendMessage(error.build()).queue();
                return;
            }
            parliamentId = "0";  // defaulting to bundestag
        }

        List<String> surveyKeys = new ArrayList<>(response.getSurveyMap().keySet());
        // https://stackoverflow.com/a/13973660/9850709
        surveyKeys.sort(new Comparator<>()
        {
            public int compare(String o1, String o2)
            {
                return extractInt(o2) - extractInt(o1);
            }

            private int extractInt(String s)
            {
                String num = s.replaceAll("\\D", "");
                // return 0 if no digits found
                return num.isEmpty() ? 0 : Integer.parseInt(num);
            }
        });

        for (String surveyKey : surveyKeys)
        {
            if (response.getSurvey(surveyKey).getParliamentId().equals(parliamentId))
            {
                cmd.getChannel().sendMessage(generateEmbed(response, parliamentId, surveyKey)).queue();
                return;
            }
        }
        throw new CommandExecuteException("No survey found for parliament \"" + parliamentId + "\".");
    }

    private MessageEmbed generateEmbed(RootDawumJson response, String parliamentId, String surveyKey)
    {
        EmbedBuilder info = new NeutralLogEmbed();
        Survey matchedSurvey = response.getSurvey(surveyKey);
        info.setDescription("**Election**: " + response.getParliament(parliamentId).getElection() + "\n" +
                "**Survey by**: " + response.getInstitute(matchedSurvey.getInstituteId()).getName() + "\n" +
                "**Survey issued by**: " + response.getTasker(matchedSurvey.getTaskerId()).getName());
        info.setFooter("Survey date: " + matchedSurvey.getDate());

        for (Map.Entry<String, Integer> entry : matchedSurvey.getResults().entrySet())
        {
            info.addField(response.getParty(entry.getKey()).getName(), entry.getValue().toString() + "%", false);
        }
        return info.build();
    }

    @Override
    public String getDescription()
    {
        return "Generates surveys";
    }

    @Override
    public String getHelpText()
    {
        return "Usage:\n" +
                "survey <parliament_id>\n\n" +
                "e.g. \"0\" is the Bundestag.\n" +
                "This returns the newest survey | \"Sonntagsfrage\".";
    }
}
