package fickle.commands;

import fickle.exceptions.FickleException;
import fickle.tasks.TaskList;
import fickle.ui.Ui;

/**
 * Represents a valid command entered by the user.
 */
public abstract class Command {
    /**
     * Executes the specific command.
     *
     * @param tasks TaskList that is working on
     * @param ui    Ui to print messages
     * @throws FickleException If the user input is invalid
     */
    public abstract void execute(TaskList tasks, Ui ui) throws FickleException;

    /**
     * Returns whether the command executed is a ByeCommand
     * 
     * @return True if the command is ByeCommand
     */
    public boolean isExit() {
        return false;
    }
}