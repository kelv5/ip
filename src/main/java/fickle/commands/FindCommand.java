package fickle.commands;

import java.util.ArrayList;

import fickle.storage.Storage;
import fickle.tasks.Task;
import fickle.tasks.TaskList;
import fickle.ui.Ui;

/**
 * Finds tasks by a keyword matching by the names of tasks in the task list.
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Initialises a command that searches for a keyword in the task name.
     * 
     * @param keyword The keyword to search for in task names.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> matchedTasks = tasks.findTasks(keyword);
        ui.printMatchedTaskList(keyword, matchedTasks);
    }
}
