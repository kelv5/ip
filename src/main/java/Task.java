public class Task {
    private String name;
    private boolean isDone;

    /**
     * Constructor for Task.
     *
     * @param name The name of the task.
     * @throws IllegalArgumentException if the task name is empty.
     */
    public Task(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Task name cannot be empty.");
        }
        this.name = name;
        this.isDone = false;

    }

    /**
     * Gets the name of the task.
     *
     * @return The taskname.
     */
    public String getName() {
        return name;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Unmark the task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns the string representation of the task.
     *
     * @return The string representation with status icon and name.
     */
    @Override
    public String toString() {
        String icon = "[" + getStatusIcon() + "] ";
        return icon + name;
    }

    private String getStatusIcon() {
        return (isDone ? "X" : " ");
    }
}