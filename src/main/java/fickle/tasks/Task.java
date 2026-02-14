package fickle.tasks;

import java.time.LocalDate;

import fickle.exceptions.FickleException;

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
     * Checks whether this task occurs on the given date.
     *
     * @param targetDate The specific target date to check on.
     * @return true if the task is schedule on the given date, else false.
     */
    public abstract boolean isScheduledOn(LocalDate targetDate);

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
     *
     * @throws FickleException If the task is already marked as done.
     */
    public void markAsDone() throws FickleException {
        if (isDone) {
            throw new FickleException("This task is already marked before.\n  " + this.toString(), "As It is");
        }

        isDone = true;
    }

    /**
     * Marks the task to set its status as not done.
     *
     * @throws FickleException If the task is already unmarked.
     */
    public void markAsNotDone() throws FickleException {
        if (!isDone) {
            throw new FickleException("This task is already unmarked before.\n  " + this.toString(), "As It is");
        }

        isDone = false;
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
