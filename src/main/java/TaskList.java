import java.util.ArrayList;

/**
 * Represents a list of tasks.
 * Manages adding and getting tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructor for TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    /**
     * Adds a task to the task list.
     * 
     * @param task The task to be added.
     * @return Message of added task.
     */
    public String addTask(Task task) {
        tasks.add(task);
        return "Added task: " + task.getName();
    }

    /**
     * Gets a task at the given index.
     * 
     * @param idx The index of the task.
     * @return The task at the index.
     */
    public Task getTask(int idx) {
        return tasks.get(idx);
    }

    /**
     * Gets the number of tasks in the task list.
     * 
     * @return The number of tasks.
     */
    public int size() {
        return tasks.size();
    }
}