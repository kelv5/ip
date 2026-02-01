package fickle.commands;

import fickle.tasks.TaskList;
import fickle.ui.Ui;
import fickle.storage.Storage;

/**
 * Lists all tasks in the task list.
 */
public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.printTaskList(tasks);
    }
}
