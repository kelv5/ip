package fickle.commands;

import java.util.ArrayList;

import fickle.storage.Storage;
import fickle.tasks.Task;
import fickle.tasks.TaskList;
import fickle.ui.Ui;

/**
 * Searches for tasks containing the keyword in their names. The search is case
 * insensitive and matches partial words.
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Initialises a command that searches for tasks with the given keyword.
     *
     * @param keyword The keyword to search for in task names.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> matchedTasks = tasks.getMatchedTasks(keyword);
        ui.printMatchedTaskList(keyword, matchedTasks);
    }
}
