package org.bundestagsbot.exceptions;

public class ConfigInvalidException extends Exception{
    public ConfigInvalidException() { }

    public ConfigInvalidException(String message) { super(message); }

    public ConfigInvalidException(String message, Exception ex) {
        super(message, ex);
    }

    public ConfigInvalidException(String message, Throwable cause) { super(message, cause); }
}
