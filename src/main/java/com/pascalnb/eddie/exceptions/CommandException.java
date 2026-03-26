package com.pascalnb.eddie.exceptions;

import net.dv8tion.jda.api.exceptions.ContextException;

public class CommandException extends Exception {

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandException(Throwable cause) {
        super(cause);
    }

    public String getPrettyError() {
        Throwable cause = this.getCause();

        if (cause != null) {
            switch (cause) {
                case CommandException commandException -> {
                    return commandException.getPrettyError();
                }
                case ContextException ignored -> {
                    return this.getMessage();
                }
                case NullPointerException ignored -> this.printStackTrace();
                default -> {
                }
            }

            return this.getMessage() + ": " + cause.getMessage();
        }

        return this.getMessage();
    }

}