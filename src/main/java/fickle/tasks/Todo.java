package fickle.tasks;

import java.time.LocalDate;

/**
 * Represents a todo task without specified time.
 */
public class Todo extends Task {
    /**
     * Constructor for Todo task.
     *
     * @param name The name of the todo task.
     */
    public Todo(String name) {
        super(name);
    }

    /**
    * Returns false by default as Todo task does not have a date.
    *
    * @param targetDate The specific target date to check on.
    * @return false for all Todo tasks.
    */
    @Override
    public boolean isScheduledOn(LocalDate targetDate) {
        return false;
    }

    /**
     * Checks whether this Todo task is a duplicate of another task.
     *
     * @param other The task to be compared with.
     * @return true if both tasks are Todo tasks sharing same name.
     */
    @Override
    public boolean isDuplicatedTask(Task other) {
        if (!(other instanceof Todo)) {
            return false;
        }

        boolean isSameName = this.getName().equals(other.getName());
        return isSameName;
    }

    /**
     * Returns the string representation of the todo task.
     *
     * @return The string representation with taskType, status icon, name.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns the string representation of the todo task to store in data file.
     *
     * @return The string representation with taskType, status, name.
     */
    @Override
    public String toStorageString() {
        return "T" + super.toStorageString();
    }
}
