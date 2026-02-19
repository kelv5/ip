package fickle.ui;

/**
 * Represents the type of a Fickle response message.
 *
 * Determines how a message should be displayed to the user with different style.
 */
public enum MessageType {
    /**
     * Represents system messages such as greetings and bye messages.
     */
    SYSTEM,

    /**
     * Represents messages for successful execution of a command.
     */
    SUCCESS,

    /**
     * Represents warnings due to corrupted saving files.
     */
    WARNING,

    /**
     * Represents error due to invalid user input or other errors.
     */
    ERROR
}
