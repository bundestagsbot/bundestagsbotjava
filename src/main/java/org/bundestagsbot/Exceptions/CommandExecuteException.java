package org.bundestagsbot.Exceptions;

public class CommandExecuteException extends Exception{
    public CommandExecuteException() { }

    public CommandExecuteException(String message, Exception ex) {
        super(message, ex);
    }

    public CommandExecuteException (String message, Throwable cause) { super(message, cause); }
}
