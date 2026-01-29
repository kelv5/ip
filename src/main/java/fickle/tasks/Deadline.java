package fickle.tasks;

public class Deadline extends Task {
    private String by;

    /**
     * Constructor for Deadline task.
     * 
     * @param name The name of the deadline task.
     * @param by   The due time of the deadline task.
     */
    public Deadline(String name, String by) {
        super(name);
        this.by = by;
    }

    /**
     * Returns the string representation of the deadline task.
     * 
     * @return The string representation with taskType, status icon, name, and due
     *         time.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}