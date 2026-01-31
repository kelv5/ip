package fickle.commands;

import fickle.tasks.TaskList;
import fickle.ui.Ui;

/**
 * Lists all tasks in the task list.
 */
public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui) {
        ui.printTaskList(tasks);
    }
}
