package fickle.commands;

import java.time.LocalDateTime;

import fickle.exceptions.FickleException;
import fickle.storage.Storage;
import fickle.tasks.Event;
import fickle.tasks.Task;
import fickle.tasks.TaskList;
import fickle.ui.Ui;

/**
 * Adds a Event task to the task list.
 */
public class EventCommand extends Command {
    private final String taskName;
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Initialises a command that adds a Event task with given name, start time
     * and end time.
     *
     * @param taskName The name of the Event task.
     * @param from The start time of the Event task.
     * @param to The end time of the Event task.
     */
    public EventCommand(String taskName, LocalDateTime from, LocalDateTime to) {
        assert !from.isAfter(to) : "Start time should not be later than end time";

        this.taskName = taskName;
        this.from = from;
        this.to = to;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws FickleException {
        Task task = new Event(taskName, from, to);
        ui.printAddedTask(tasks.addTask(task), tasks.getSize());
        storage.appendSave(task);
    }
}
