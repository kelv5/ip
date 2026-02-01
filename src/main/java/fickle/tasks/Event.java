package fickle.tasks;

public class Event extends Task {
    private String from;
    private String to;

    /**
     * Constructor for Event task.
     * 
     * @param name The name of the event task.
     * @param from The start time of the event task.
     * @param to   The end time of the event task.
     */
    public Event(String name, String from, String to) {
        super(name);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the string representation of the deadline task.
     * 
     * @return The string representation with taskType, status icon, name, and due
     *         time.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (FROM: " + from + "  TO: " + to + ")";
    }

    @Override
    public String toStorageString() {
        return "E" + super.toStorageString() + " | " + from + " | " + to;
    }
}