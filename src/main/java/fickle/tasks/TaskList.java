package fickle.tasks;

import java.util.ArrayList;

/**
 * Represents a list of tasks. Manages adding and getting tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructor for TaskList with the list of tasks.
     *
     * @param tasks The loaded list of tasks from save file.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Alternative constructor for TaskList to create an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the task list.
     * 
     * @param task The task to be added.
     * @return Message of added task.
     */
    public String addTask(Task task) {
        tasks.add(task);
        return task.toString();
    }

    /**
     * Delete a task from the task list.
     * 
     * @param index The index of the task to be deleted.
     * @return Message of deleted task.
     */
    public Task deleteTask(int index) {
        return tasks.remove(index);
    }

    /**
     * Gets a task at the given index.
     * 
     * @param idx The index of the task.
     * @return The task at the index.
     */
    public Task getTask(int idx) {
        return this.tasks.get(idx);
    }

    /**
     * Gets the number of tasks in the task list.
     * 
     * @return The number of tasks.
     */
    public int getSize() {
        return this.tasks.size();
    }

    /**
     * Checks whether all tasks in the list are marked as done.
     *
     * @return true if all tasks are completed, else it returns false
     */
    public boolean isAllMarked() {
        for (Task task : tasks) {
            if (!task.isDone()) {
                return false;
            }
        }
        return true;
    }
}