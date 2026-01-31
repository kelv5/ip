package fickle.commands;

import fickle.tasks.Task;
import fickle.tasks.TaskList;
import fickle.tasks.Todo;
import fickle.ui.Ui;

/**
 * Adds a Todo task to the task list.
 */
public class TodoCommand extends Command {
    private final String taskName;

    /**
     * Initialises a command that adds a Todo task with given name.
     * 
     * @param taskName The name of the Todo task
     */
    public TodoCommand(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) {
        Task task = new Todo(taskName);
        ui.printAddedTask(tasks.addTask(task), tasks.getSize());
    }
}