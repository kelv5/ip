package fickle.commands;

import fickle.exceptions.FickleException;
import fickle.storage.Storage;
import fickle.tasks.TaskList;
import fickle.ui.Ui;

/**
 * Represents a valid command entered by the user.
 */
public abstract class Command {

    /**
     * Executes the specific command.
     *
     * @param tasks TaskList that is working on.
     * @param ui Ui to print messages.
     * @param storage Storage to save task in file.
     * @throws FickleException If the user input is invalid.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws FickleException;

    /**
     * Returns whether the command executed is a ByeCommand.
     *
     * @return True if the command is ByeCommand.
     */
    public boolean isExit() {
        return false;
    }
}
