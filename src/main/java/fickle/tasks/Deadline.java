package fickle.tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents a deadline task that has a due date and time.
 */
public class Deadline extends Task {
    // Locale.ENGLISH ensures that AM/PM is uppercase
    private static final DateTimeFormatter DEADLINE_DISPLAY_FORMAT = DateTimeFormatter
                                    .ofPattern("EEE, MMM dd yyyy '['h:mma']'", Locale.ENGLISH);
    private static final DateTimeFormatter STORAGE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

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

    private LocalDateTime getByDate() {
        return by;
    }

    /**
    * Returns true if this deadline occurs on the given date.
    *
    * @param targetDate The specific target date to check on.
    * @return true if the deadline date equals the given date, else false.
    */
    @Override
    public boolean isScheduledOn(LocalDate targetDate) {
        return by.toLocalDate().equals(targetDate);
    }

    /**
     * Checks whether this Deadline task is a duplicate of another task.
     *
     * @param other The task to be compared with.
     * @return true if both tasks are Deadline tasks with same name and due datetime.
     */
    @Override
    public boolean isDuplicatedTask(Task other) {
        if (!(other instanceof Deadline)) {
            return false;
        }

        Deadline otherDeadline = (Deadline) other;

        boolean isSameName = this.getName().equals(otherDeadline.getName());
        boolean isSameByDate = this.getByDate().equals(otherDeadline.getByDate());

        return isSameName && isSameByDate;
    }

    /**
     * Returns the string representation of the deadline task.
     *
     * @return The string representation with taskType, status, name and due
     *         date/ time (Example DateTime: Sat, Aug 21 2021 [10:50PM]).
     */
    @Override
    public String toString() {
        String outputBy = by.format(DEADLINE_DISPLAY_FORMAT);
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
        String storageBy = by.format(STORAGE_FORMAT);
        return "D" + super.toStorageString() + " | " + storageBy;
    }
}
