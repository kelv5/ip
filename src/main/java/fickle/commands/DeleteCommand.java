package fickle.commands;

import fickle.exceptions.FickleException;
import fickle.tasks.Task;
import fickle.tasks.TaskList;
import fickle.ui.Ui;
import fickle.storage.Storage;

/**
 * Deletes a task in the task list.
 */
public class DeleteCommand extends Command {
    private final int taskIndex;

    /**
     * Initialises a command that delete the task at the given index.
     * 
     * @param taskIndex The index of the task to be deleted, starting from 0
     */
    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws FickleException {
        if (tasks.getSize() == 0) {
            throw new FickleException("No tasks remaining to delete.", "A Little Happiness");
        }
        if (taskIndex < 0) {
            throw new FickleException(
                    "Invalid task index: less than 1.", "Too small, Insignificance");
        }

        if (taskIndex >= tasks.getSize()) {
            throw new FickleException(
                    "Invalid task index: exceeds the total number of tasks.", "Too Much");
        }

        Task task = tasks.deleteTask(taskIndex);
        int totalTaskCount = tasks.getSize();
        ui.printDeletedTask("  " + task.toString(), totalTaskCount);

        // Special message when all tasks are deleted
        if (totalTaskCount == 0) {
            ui.printSingleLineWithoutLine("Wow! All tasks are deleted!");
            ui.printEasterAlignedRight("A Little Happiness");
        }
        storage.overwriteSave(tasks);
    }
}