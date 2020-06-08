package org.bundestagsbot.Log;

import org.apache.logging.log4j.core.LogEvent;

@FunctionalInterface
public interface ILogObserver
{
    void onNewLogEvent(LogEvent event);
}

