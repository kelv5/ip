package fickle.tasks;

/** Abstract class representing a task */
public abstract class Task {
    private String name;
    private boolean isDone;

    /**
     * Constructor for Task.
     *
     * @param name The name of the task.
     */
    public Task(String name) {
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
     * Marks the task to set its status as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Mark the task to set its status as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns the completion status of the task.
     * 
     * @return True if the task is marked as done, else False.
     */
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

    /**
     * Returns the string representation of the task for saving to a file.
     *
     * @return The string representation for writing to the save file.
     */
    public String toStorageString() {
        String statusIcon = (isDone()) ? "1" : "0";
        return " | " + statusIcon + " | " + getName();
    }

    private String getStatusIcon() {
        return (isDone) ? "X" : " ";
    }
}
