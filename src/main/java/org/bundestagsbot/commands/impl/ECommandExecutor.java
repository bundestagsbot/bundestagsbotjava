package org.bundestagsbot.commands.impl;

import com.github.g3force.instanceables.IInstanceableEnum;
import com.github.g3force.instanceables.InstanceableClass;

public enum ECommandExecutor implements IInstanceableEnum
{
    ABOUT(new InstanceableClass(CommandAbout.class)),

    HELP(new InstanceableClass(CommandHelp.class)),

    SURVEY(new InstanceableClass(CommandSurvey.class)),

    ;

    private final InstanceableClass impl;


    ECommandExecutor(final InstanceableClass impl, final ECommandExecutor... dependencies)
    {
        this.impl = impl;
    }


    @Override
    public InstanceableClass getInstanceableClass()
    {
        return impl;
    }

}