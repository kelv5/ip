package fickle.commands;

import fickle.exceptions.FickleException;
import fickle.tasks.Task;
import fickle.tasks.TaskList;
import fickle.ui.Ui;
import fickle.storage.Storage;

/**
 * Unmarks a task in the task list.
 */
public class UnmarkCommand extends Command {
    private final int taskIndex;

    /**
     * Initialises a command that unmarks the task at the given index.
     * 
     * @param taskIndex The index of the task to be unmarked, starting from 0
     */
    public UnmarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws FickleException {
        if (tasks.getSize() == 0) {
            throw new FickleException("There are no tasks left to unmark.", "A Little Happiness");
        }
        if (taskIndex < 0) {
            throw new FickleException(
                    "Task number starts from 1. Please enter a valid number.", "Too small, Insignificance");
        }

        if (taskIndex >= tasks.getSize()) {
            throw new FickleException(
                    "Please enter a task number within the available range.", "Too Much");
        }

        Task task = tasks.getTask(taskIndex);
        task.markAsNotDone();
        ui.printUnmarkedTask(task.toString());
        storage.overwriteSave(tasks);
    }
}
