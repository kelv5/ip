package fickle.commands;

import fickle.exceptions.FickleException;
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
     * @param taskIndex The index of the task to be marked, starting from 0
     */
    public MarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws FickleException {
        if (tasks.getSize() == 0) {
            throw new FickleException("No tasks remaining to mark.", "A Little Happiness");
        }
        if (taskIndex < 0) {
            throw new FickleException(
                    "Invalid task index: less than 1.", "Too small, Insignificance");
        }

        if (taskIndex >= tasks.getSize()) {
            throw new FickleException(
                    "Invalid task index: exceeds the total number of tasks.", "Too Much");
        }

        Task task = tasks.getTask(taskIndex);
        task.markAsDone();
        ui.printMarkedTask(task.toString());

        // Special message when all tasks are completed
        if (tasks.isAllMarked()) {
            ui.printSingleLineWithoutLine("Congratulations! All tasks are completed!");
            ui.printEasterAlignedRight("A Little Happiness");
        }
    }
}
