package fickle.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import fickle.exceptions.FickleException;
import fickle.storage.Storage;
import fickle.tasks.Task;
import fickle.tasks.TaskList;
import fickle.tasks.Todo;
import fickle.ui.Ui;

public class MarkCommandTest {
    @Test
    public void markCommand_taskMarkedDone_success() throws FickleException {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("data/test.txt");

        Task task = new Todo("testing todo");
        tasks.addTask(task);

        MarkCommand cmd = new MarkCommand(0);
        cmd.execute(tasks, ui, storage);

        assertTrue(task.isDone());
    }

    @Test
    public void markCommand_emptyList_exceptionThrown() {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("data/test.txt");
        MarkCommand cmd = new MarkCommand(0);

        try {
            cmd.execute(tasks, ui, storage);
            fail();
        } catch (FickleException e) {
            assertEquals("There are no tasks left to mark.", e.getMessage());
        }
    }
}
