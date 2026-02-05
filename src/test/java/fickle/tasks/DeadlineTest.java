package fickle.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class DeadlineTest {

    @Test
    public void deadline_toString_success() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        LocalDateTime outputBy = LocalDateTime.parse("21/8/2021 1800", dateTimeFormatter);

        Deadline deadline = new Deadline("testing task", outputBy);

        assertEquals("[D][ ] testing task (BY: Sat, Aug 21 2021 [6:00PM])", deadline.toString());
    }

    @Test
    public void deadline_toStorageString_success() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        LocalDateTime storageBy = LocalDateTime.parse("21/8/2021 1800", dateTimeFormatter);

        Deadline deadline = new Deadline("testing task", storageBy);

        assertEquals("D | 0 | testing task | 2021-08-21 1800", deadline.toStorageString());
    }
}
