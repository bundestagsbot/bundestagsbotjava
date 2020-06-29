package org.bundestagsbot.commands.impl;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.bundestagsbot.commands.Command;
import org.bundestagsbot.config.BotConfigSingleton;
import org.bundestagsbot.embeds.ErrorLogEmbed;
import org.bundestagsbot.exceptions.CommandExecuteException;

import java.util.List;

public class CommandRole extends ACommandExecutor {

    private static final String WHITE_CHECK_MARK = "\u2705";

    public CommandRole() { super(ChannelType.TEXT); }

    @Override
    public void onExecute(Command cmd, JDA jda) throws CommandExecuteException {
        if (cmd.getArgs().size() != 2) {
            ErrorLogEmbed errorLogEmbed = new ErrorLogEmbed();
            errorLogEmbed.setDescription("Invalid number of arguments, please refer to: `help role`");
            cmd.getChannel().sendMessage(errorLogEmbed.build()).queue();
            return;
        }

        if (ChannelType.PRIVATE.equals(cmd.getChannel().getType())) {
            ErrorLogEmbed errorLogEmbed = new ErrorLogEmbed();
            errorLogEmbed.setDescription("This command is only usable on a discord server.");
            cmd.getChannel().sendMessage(errorLogEmbed.build()).queue();
            return;
        }

        List<Role> foundRoles = cmd.getGuild().get().getRolesByName(cmd.getArgs().get(1), true);
        if (foundRoles.isEmpty()) {
            ErrorLogEmbed errorLogEmbed = new ErrorLogEmbed();
            errorLogEmbed.setDescription("Invalid role specified.");
            cmd.getChannel().sendMessage(errorLogEmbed.build()).queue();
            return;
        }
        Role specifiedRole = foundRoles.get(0);
        if (!BotConfigSingleton.getInstance().getConfig().getAssignableRoles().contains(specifiedRole.getId())) {
            ErrorLogEmbed errorLogEmbed = new ErrorLogEmbed();
            errorLogEmbed.setDescription("This role is not assignable.");
            cmd.getChannel().sendMessage(errorLogEmbed.build()).queue();
            return;
        }

        if (cmd.getGuild().get().getMember(cmd.getAuthor()).getRoles().contains(specifiedRole)) {
            // user already has this role
            cmd.getGuild().get().removeRoleFromMember(cmd.getAuthor().getId(), specifiedRole).queue(success -> {
                cmd.getMessage().addReaction(WHITE_CHECK_MARK).queue();
            });
        } else {
            // user does not have this role yet
            cmd.getGuild().get().addRoleToMember(cmd.getAuthor().getId(), specifiedRole).queue(success -> {
                cmd.getMessage().addReaction(WHITE_CHECK_MARK).queue();
            });
        }
    }

    @Override
    public String getDescription() {
        return "This assigns you a role";
    }

    @Override
    public String getHelpText() {
        return "Usage:\n" +
                "role <role_name>\n\n" +
                "Assigns you the specified role or removes it if you already got it.";
    }
}
