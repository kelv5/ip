package fickle.commands;

import fickle.exceptions.FickleException;
import fickle.storage.Storage;
import fickle.tasks.Task;
import fickle.tasks.TaskList;
import fickle.ui.Ui;

/**
 * Marks a task in the task list as done.
 */
public class MarkCommand extends Command {
    private final int taskIndex;

    /**
     * Initialises a command that marks the task at the given index as done.
     *
     * @param taskIndex The index of the task to be marked, starting from 0.
     */
    public MarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws FickleException {
        if (tasks.getSize() == 0) {
            throw new FickleException("There are no tasks left to mark.", "A Little Happiness");
        }

        if (taskIndex < 0) {
            throw new FickleException("Task number starts from 1. Please enter a valid number.",
                                            "Too small, Insignificance");
        }

        if (taskIndex >= tasks.getSize()) {
            throw new FickleException("Please enter a task number within the available range.", "Too Much");
        }

        Task task = tasks.getTask(taskIndex);
        task.markAsDone();
        ui.printMarkedTask(task.toString());

        // Special message when all tasks are completed
        if (tasks.isAllMarked()) {
            ui.printSingleLineWithoutLine("Congratulations! All tasks are completed!");
            ui.printEasterAlignedRight("A Little Happiness");
        }

        storage.overwriteSave(tasks);
    }
}
