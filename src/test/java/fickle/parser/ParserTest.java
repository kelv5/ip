package fickle.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import fickle.commands.ByeCommand;
import fickle.commands.Command;
import fickle.commands.DeleteCommand;
import fickle.commands.MarkCommand;
import fickle.exceptions.FickleException;

public class ParserTest {

    @Test
    public void parse_emptyInput_exceptionThrown() {
        try {
            Parser.parse("");
            fail();
        } catch (FickleException e) {
            assertEquals("No input detected. We'll be here when you're ready.", e.getMessage());
        }
    }

    @Test
    public void parse_invalidCommand_exceptionThrown() {
        try {
            Parser.parse("illegalCommand");
            fail();
        } catch (FickleException e) {
            assertEquals("Sorry, I didn't understand that. Try a valid command!", e.getMessage());
        }
    }

    @Test
    public void parse_byeCommand_sucess() throws FickleException {
        Command c = Parser.parse("bye");
        assertTrue(c instanceof ByeCommand);
        assertTrue(c.isExit());
    }

    @Test
    public void parse_invalidListCommand_exceptionThrown() {
        try {
            Parser.parse("list 1");
            fail();
        } catch (FickleException e) {
            assertEquals("The 'list' command doesn't take any arguments.", e.getMessage());
        }
    }

    @Test
    public void parse_markCommandEmptyInput_throwsException() {
        try {
            Parser.parse("mark");
            fail();
        } catch (FickleException e) {
            assertEquals("Please provide a task number for the 'mark' command.", e.getMessage());
        }
    }

    @Test
    public void parse_markValidInput_returnsMarkCommand() throws FickleException {
        Command c = Parser.parse("mark 1");
        assertTrue(c instanceof MarkCommand);
    }

    @Test
    public void parse_unmarkCommandNonIntegerInput_throwsException() {
        try {
            Parser.parse("unmark 3.3");
            fail();
        } catch (FickleException e) {
            assertEquals("Please use a valid whole number for the task.", e.getMessage());
        }
    }

    @Test
    public void parse_todoCommandNonEmptyName_throwsException() {
        try {
            Parser.parse("todo   ");
            fail();
        } catch (FickleException e) {
            assertEquals("Todo name is missing. Please provide one.", e.getMessage());
        }
    }

    @Test
    public void parse_deadlineCommandWithoutBy_exceptionThrown() {
        try {
            Parser.parse("deadline project 3/9/2025 1820");
            fail();
        } catch (FickleException e) {
            assertEquals("Deadline must include '/by' to set a due time.", e.getMessage());
        }
    }

    @Test
    public void parse_deadlineCommandInvalidDateFormat_exceptionThrown() {
        try {
            Parser.parse("deadline project /by 2025-01-01");
            fail();
        } catch (FickleException e) {
            String s = "Date/time format incorrect. Please use: d/M/yyyy or d/M/yyyy HHmm";
            assertTrue(e.getMessage().contains(s));
        }
    }

    @Test
    public void parse_eventCommandStartAfterEnd_exceptionThrown() {
        try {
            Parser.parse("event party /from 4/9/2026 2000 /to 3/9/2026 1800");
            fail();
        } catch (FickleException e) {
            assertEquals("Start time cannot be later than end time.", e.getMessage());
        }
    }

    @Test
    public void parse_deleteCommand_success() throws FickleException {
        Command c = Parser.parse("delete 10");
        assertTrue(c instanceof DeleteCommand);
    }
}
