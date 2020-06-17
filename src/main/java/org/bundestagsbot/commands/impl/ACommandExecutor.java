package org.bundestagsbot.commands.impl;

import net.dv8tion.jda.api.entities.ChannelType;
import org.bundestagsbot.commands.CommandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ACommandExecutor implements ICommandExecutor
{
    private CommandHandler parent;

    protected CommandHandler getParent()
    {
        return parent;
    }

    public void setParent(CommandHandler parent)
    {
        this.parent = parent;
    }

    private final List<ChannelType> allowedChannelTypes = new ArrayList<>();

    @Override
    public List<ChannelType> getAllowedChannelTypes() { return allowedChannelTypes; }

    protected ACommandExecutor(ChannelType... channels) {
        allowedChannelTypes.addAll(Arrays.asList(channels));
    }
}
