package fickle.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Constructor for Event task.
     * 
     * @param name The name of the event task.
     * @param from The start time of the event task.
     * @param to   The end time of the event task.
     */
    public Event(String name, LocalDateTime from, LocalDateTime to) {
        super(name);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the string representation of the event task.
     * 
     * @return The string representation with taskType, status, name and due date/
     *         time (Example DateTime: Aug 21 2021 [10:50PM]).
     */
    @Override
    public String toString() {
        // Locale.ENGLISH to ensure AM/PM is uppercase
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy '['h:mma']'", Locale.ENGLISH);
        String outputFrom = from.format(outputFormatter);
        String outputTo = to.format(outputFormatter);
        return "[E]" + super.toString() + " (FROM: " + outputFrom + "  TO: " + outputTo + ")";
    }

    /**
     * Returns the string representation of the event task to store in data file.
     *
     * @return The string representation with taskType, status, name and due
     *         date/time for storage. (Example DateTime: 2021-08-21 2250).
     */
    @Override
    public String toStorageString() {
        DateTimeFormatter storageFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        String storageFrom = from.format(storageFormatter);
        String storageTo = to.format(storageFormatter);
        return "E" + super.toStorageString() + " | " + storageFrom + " | " + storageTo;
    }
}