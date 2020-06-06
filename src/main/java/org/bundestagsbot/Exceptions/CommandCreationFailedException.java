package org.bundestagsbot.Exceptions;

public class CommandCreationFailedException extends Exception{
    public CommandCreationFailedException () { }

    public CommandCreationFailedException (String message) {
        super(message);
    }

    public CommandCreationFailedException (String message, Throwable cause) { super(message, cause); }
}
