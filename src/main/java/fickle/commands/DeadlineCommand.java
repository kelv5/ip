package fickle.commands;

import java.time.LocalDateTime;

import fickle.exceptions.FickleException;
import fickle.storage.Storage;
import fickle.tasks.Deadline;
import fickle.tasks.Task;
import fickle.tasks.TaskList;
import fickle.ui.Ui;

/**
 * Adds a Deadline task to the task list.
 */
public class DeadlineCommand extends Command {
    private final String taskName;
    private final LocalDateTime by;

    /**
     * Initialises a command that adds a Deadline task with given name and due
     * time.
     * 
     * @param taskName The name of the Deadline task.
     * @param by The due time of the Deadline task.
     */
    public DeadlineCommand(String taskName, LocalDateTime by) {
        this.taskName = taskName;
        this.by = by;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws FickleException {
        Task task = new Deadline(taskName, by);
        ui.printAddedTask(tasks.addTask(task), tasks.getSize());
        storage.appendSave(task);
    }
}
