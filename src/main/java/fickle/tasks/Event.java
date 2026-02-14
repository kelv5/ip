package fickle.tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents an event task that has a start and end date and time.
 */
public class Event extends Task {
    // Locale.ENGLISH ensures that AM/PM is uppercase
    private static final DateTimeFormatter STORAGE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter EVENTS_DISPLAY_FORMAT = DateTimeFormatter
                                    .ofPattern("MMM dd yyyy '['h:mma']'", Locale.ENGLISH);

    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Constructor for Event task.
     *
     * @param name The name of the event task.
     * @param from The start time of the event task.
     * @param to The end time of the event task.
     */
    public Event(String name, LocalDateTime from, LocalDateTime to) {
        super(name);
        this.from = from;
        this.to = to;
    }

    /**
    * Returns true if this event occurs on the given date.
    *
    * @param targetDate The specific target date to check on.
    * @return true if the date is within the event period (inclusive of bounds).
    */
    @Override
    public boolean isScheduledOn(LocalDate targetDate) {
        boolean isAfterStartDate = !(targetDate.isBefore(from.toLocalDate()));
        boolean isBeforeEndDate = !(targetDate.isAfter(to.toLocalDate()));

        return isAfterStartDate && isBeforeEndDate;
    }

    /**
     * Returns the string representation of the event task.
     *
     * @return The string representation with taskType, status, name and due
     *         date/ time (Example DateTime: Aug 21 2021 [10:50PM]).
     */
    @Override
    public String toString() {
        String outputFrom = from.format(EVENTS_DISPLAY_FORMAT);
        String outputTo = to.format(EVENTS_DISPLAY_FORMAT);

        return "[E]" + super.toString() + " (FROM: " + outputFrom + "  TO: " + outputTo + ")";
    }

    /**
     * Returns the string representation of the event task to store in file.
     *
     * @return The string representation with taskType, status, name and due
     *         date/time for storage. (Example DateTime: 2021-08-21 2250).
     */
    @Override
    public String toStorageString() {
        String storageFrom = from.format(STORAGE_FORMAT);
        String storageTo = to.format(STORAGE_FORMAT);

        return "E" + super.toStorageString() + " | " + storageFrom + " | " + storageTo;
    }
}
