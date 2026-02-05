package fickle.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import fickle.commands.ByeCommand;
import fickle.commands.Command;
import fickle.commands.DeadlineCommand;
import fickle.commands.DeleteCommand;
import fickle.commands.EventCommand;
import fickle.commands.ListCommand;
import fickle.commands.MarkCommand;
import fickle.commands.TodoCommand;
import fickle.commands.UnmarkCommand;
import fickle.exceptions.FickleException;

/**
 * Converts user input into a corresponding command.
 */
public class Parser {

    /**
     * Parses a input string from the user and returns a corresponding command.
     * 
     * @param input Input string entered by the user
     * @return A specifc command object corresponding to the input.
     * @throws FickleException if the input is invalid or unknown command
     */
    public static Command parse(String input) throws FickleException {
        if (input.isEmpty()) {
            throw new FickleException("No input detected. We'll be here when you're ready.", "Don't Leave me Alone.");
        }

        if (input.contains("|")) {
            throw new FickleException("Please avoid using '|'. Remove it and try again.", "Let it...");
        }

        String[] parts = input.split(" ", 2);
        String commandWord = parts[0].toLowerCase();
        String contextWord = (parts.length > 1) ? parts[1].trim() : "";

        // contextWord is everything after the single command word
        switch (commandWord) {
        case "bye":
            return parseBye(contextWord);

        case "list":
            return parseList(contextWord);

        case "mark":
            return parseMark(contextWord);

        case "unmark":
            return parseUnmark(contextWord);

        case "deadline":
            return parseDeadline(contextWord);

        case "todo":
            return parseTodo(contextWord);

        case "event":
            return parseEvent(contextWord);

        case "delete":
            return parseDelete(contextWord);

        default:
            throw new FickleException("Sorry, I didn't understand that. Try a valid command!", "Going Nowhere.");
        }
    }

    private static Command parseBye(String contextWord) throws FickleException {
        if (!contextWord.isEmpty()) {
            throw new FickleException("The 'bye' command doesn't take any arguments.", "Even fickleness has rules");
        }

        return new ByeCommand();
    }

    private static Command parseList(String contextWord) throws FickleException {
        if (!contextWord.isEmpty()) {
            throw new FickleException("The 'list' command doesn't take any arguments.", "Even fickleness has rules");
        }

        return new ListCommand();
    }

    private static Command parseMark(String contextWord) throws FickleException {
        if (contextWord.isEmpty()) {
            throw new FickleException("Please provide a task number for the 'mark' command.",
                                            "Shadow's shadow without it");
        }
        try {
            int index = Integer.parseInt(contextWord) - 1;
            return new MarkCommand(index);
        } catch (NumberFormatException e) {
            throw new FickleException("Please use a valid whole number for the task.", "Confuses me, Contradiction");
        }
    }

    private static Command parseUnmark(String contextWord) throws FickleException {
        if (contextWord.isEmpty()) {
            throw new FickleException("Please provide a task number for the 'unmark' command.",
                                            "Shadow's shadow without it");
        }
        try {
            int index = Integer.parseInt(contextWord) - 1;
            return new UnmarkCommand(index);
        } catch (NumberFormatException e) {
            throw new FickleException("Please use a valid whole number for the task.", "Confuses me, Contradiction");
        }
    }

    private static Command parseDeadline(String contextWord) throws FickleException {
        if (contextWord.isEmpty()) {
            throw new FickleException("Deadline name is missing. Please provide one.", "Like Missing You");
        }

        if (!contextWord.contains("/by")) {
            throw new FickleException("Deadline must include '/by' to set a due time.", "Too Late without it");
        }

        String[] contents = contextWord.split("/by", 2);
        String name = contents[0].trim();
        String byString = contents[1].trim();

        if (name.isEmpty() || byString.isEmpty()) {
            throw new FickleException("Deadline needs both a name and a due time.", "else, it's Untold");
        }

        LocalDateTime by = parseDateTime(byString);

        return new DeadlineCommand(name, by);
    }

    private static Command parseTodo(String contextWord) throws FickleException {
        if (contextWord.isEmpty()) {
            throw new FickleException("Todo name is missing. Please provide one.", "Like Missing You");
        }

        return new TodoCommand(contextWord);
    }

    private static Command parseEvent(String contextWord) throws FickleException {
        if (contextWord.isEmpty()) {
            throw new FickleException("Event name is missing. Please provide one.", "Like Missing You");
        }

        if (!contextWord.contains("/from") || !contextWord.contains("/to")) {
            throw new FickleException("Event must include both '/from' and '/to'.", "Too Late without it");
        }

        if (contextWord.indexOf("/from") > contextWord.indexOf("/to")) {
            throw new FickleException("/from has to be in front of /to.", "Confuses me, Contradiction");
        }

        String[] firstSecondParts = contextWord.split("/from", 2);
        String name = firstSecondParts[0].trim();
        String[] secondThirdParts = firstSecondParts[1].split("/to", 2);
        String fromString = secondThirdParts[0].trim();
        String toString = secondThirdParts[1].trim();

        if (name.isEmpty() || fromString.isEmpty() || toString.isEmpty()) {
            throw new FickleException("Event needs a name, a start time, and an end time.", "else, it's Untold");
        }

        LocalDateTime from = parseDateTime(fromString);
        LocalDateTime to = parseDateTime(toString);

        if (from.isAfter(to)) {
            throw new FickleException("Start time cannot be later than end time.", "As It is");
        }

        return new EventCommand(name, from, to);
    }

    private static Command parseDelete(String contextWord) throws FickleException {
        if (contextWord.isEmpty()) {
            throw new FickleException("Please provide a task number for the 'delete' command.",
                                            "Shadow's shadow without it");
        }

        try {
            int index = Integer.parseInt(contextWord) - 1;
            return new DeleteCommand(index);
        } catch (NumberFormatException e) {
            throw new FickleException("Please use a valid whole number for the task.", "Confuses me, Contradiction");
        }
    }

    private static LocalDateTime parseDateTime(String dateTimeString) throws FickleException {
        DateTimeFormatter dateOnlyFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

        try {
            if (dateTimeString.contains(" ")) {
                return LocalDateTime.parse(dateTimeString, dateTimeFormatter);
            } else {
                // Time is optional. Default time is 00:00 if omitted by user.
                LocalDate date = LocalDate.parse(dateTimeString, dateOnlyFormatter);
                return date.atTime(0, 0);
            }
        } catch (DateTimeParseException e) {
            String exceptionMsg = "Date/time format incorrect. Please use: d/M/yyyy or d/M/yyyy HHmm.\n"
                                            + "[Example Usage]  21/8/2021 or 3/9/2010 0911 (Default time: 00:00)";

            throw new FickleException(exceptionMsg, "Time will Tell");
        }
    }
}
