package org.bundestagsbot.commands.impl;

import org.bundestagsbot.commands.CommandHandler;

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
}
