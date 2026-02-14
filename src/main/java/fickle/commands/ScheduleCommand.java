package fickle.commands;

import java.time.LocalDate;
import java.util.ArrayList;

import fickle.storage.Storage;
import fickle.tasks.Task;
import fickle.tasks.TaskList;
import fickle.ui.Ui;

/**
 * Searches for all tasks scheduled on a specific date.
 */
public class ScheduleCommand extends Command {
    private final LocalDate targetDate;

    /**
     * Initialises a command that searches for tasks occuring on a specific date.
     *
     * @param targetDate The date to view the scheduled tasks for.
     */
    public ScheduleCommand(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> scheduledTasks = tasks.getScheduledOnTasks(targetDate);
        ui.printScheduledTaskList(targetDate, scheduledTasks);
    }
}
