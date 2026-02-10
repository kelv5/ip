package fickle.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents a deadline task that has a due date and time.
 */
public class Deadline extends Task {
    private LocalDateTime by;

    /**
     * Constructor for Deadline task.
     *
     * @param name The name of the deadline task.
     * @param by The due time of the deadline task.
     */
    public Deadline(String name, LocalDateTime by) {
        super(name);
        this.by = by;
    }

    /**
     * Returns the string representation of the deadline task.
     *
     * @return The string representation with taskType, status, name and due
     *         date/ time (Example DateTime: Sat, Aug 21 2021 [10:50PM]).
     */
    @Override
    public String toString() {
        // Locale.ENGLISH to ensure AM/PM is uppercase
        String outputBy = by.format(DateTimeFormatter.ofPattern("EEE, MMM dd yyyy '['h:mma']'", Locale.ENGLISH));
        return "[D]" + super.toString() + " (BY: " + outputBy + ")";
    }

    /**
     * Returns the string representation of the deadline task to store in file.
     *
     * @return The string representation with taskType, status, name and due
     *         date/time for storage. (Example DateTime: 2021-08-21 2250).
     */
    @Override
    public String toStorageString() {
        String storageBy = by.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        return "D" + super.toStorageString() + " | " + storageBy;
    }
}
