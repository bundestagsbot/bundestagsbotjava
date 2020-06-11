package org.bundestagsbot.log;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

import java.util.ArrayList;
import java.util.List;

@Plugin(
        name = "MapAppender",
        category = Core.CATEGORY_NAME,
        elementType = Appender.ELEMENT_TYPE)
public class LogRouter extends AbstractAppender
{
    private static LogRouter INSTANCE;

    private final List<ILogObserver> observers = new ArrayList<>();

    protected LogRouter(String name, Filter filter)
    {
        super(name, filter, null, false, null);
    }

    @PluginFactory
    public static LogRouter createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Filter") Filter filter)
    {
        INSTANCE = new LogRouter(name, filter);
        return INSTANCE;
    }

    public static LogRouter getInstance()
    {
        return INSTANCE;
    }

    @Override
    public void append(LogEvent event)
    {
        // forward event
        observers.forEach(e -> e.onNewLogEvent(event));
    }

    public void addObserver(ILogObserver observer)
    {
        observers.add(observer);
    }
}
