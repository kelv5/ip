package fickle.ui;

import java.util.ArrayList;
import java.util.Scanner;

import fickle.tasks.Task;
import fickle.tasks.TaskList;

/**
 * UI class for handling user interactions.
 */
public class Ui {
    public static final String LINE = "___________________________________________________________________________________________________";
    private static final String INDENTATION = "      ";
    private Scanner scanner;

    /**
     * Constructor for Ui.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the ASCII art logo.
     */
    public void printLogo() {
        indentLine();
        String logo = "       ______ _      _    _\n" + "      |  ___(_)    | |  | |     \n"
                                        + "      | |_   _  ___| | _| | ___ \n" + "      |  _| | |/ __| |/ / |/ _ \\\n"
                                        + "      | |   | | |__|   <|_|  __/\n"
                                        + "      \\_|   |_|\\___|_|\\_\\_|\\___|\n";
        System.out.println(logo);
    }

    /**
     * Prints Fickle's greeting message.
     */
    public void greet() {
        printFormattedMessages(new String[] { "Hi! I'm Fickle", "What feels right to start with today? " });
        indentLine();
    }

    /**
     * Prints Fickle's goodbye message.
     */
    public void sayGoodbye() {
        printFormattedMessages(new String[] { "Goodbye. See you again soon! " });
        printEasterAlignedRight("Day by Day");
        scanner.close();
    }

    /**
     * Reads a line of user input.
     * 
     * @return The user input.
     */
    public String readInput() {
        System.out.println();
        System.out.print("Next:  ");
        String input = scanner.nextLine();
        System.out.println();
        return input;
    }

    /**
     * Prints the added task message.
     * 
     * @param taskname The name of the added task.
     * @param totalTasks Total number of tasks after added a new task
     * 
     */
    public void printAddedTask(String taskname, int totalTasks) {
        String totalTasksMessage = "Now you have " + totalTasks + " task" + ((totalTasks == 1) ? "" : "s")
                                        + " in the list.";
        printFormattedMessages(new String[] { "Got it. I've added this task: ", "  " + taskname, "\n",
                totalTasksMessage });
        printEasterAlignedRight("Still Early");
    }

    /**
     * Prints the deleted task message.
     * 
     * @param taskname The name of the deleted task.
     * @param totalTasks Total number of tasks after deleted a new task
     * 
     */
    public void printDeletedTask(String taskname, int totalTasks) {
        String totalTaskMessage = "Now you have " + totalTasks + " task" + ((totalTasks == 1) ? "" : "s")
                                        + " in the list.";
        printFormattedMessages(new String[] { "Noted. I've removed this task: ", "  " + taskname, "\n",
                totalTaskMessage });
        printEasterAlignedRight("It's Gone");
    }

    /**
     * Prints the marked task message.
     * 
     * @param taskname The name of the marked task.
     */
    public void printMarkedTask(String taskname) {
        printFormattedMessages(new String[] { "All set. This task is marked as done: ", taskname });
        printEasterAlignedRight("One After Another");
    }

    /**
     * Prints the unmarked task message.
     * 
     * @param taskname The name of the unmarked task.
     */
    public void printUnmarkedTask(String taskname) {
        printFormattedMessages(new String[] { "Noted. This task is now unmarked: ", taskname });
        printEasterAlignedRight("Pace Yourself");
    }

    /**
     * Prints the list of tasks.
     * 
     * @param tasks The tasklist to be printed.
     */
    public void printTaskList(TaskList tasks) {
        if (tasks.getSize() == 0) {
            printFormattedMessages(new String[] { "No tasks remaining. " });
            printEasterAlignedRight("A Little Happiness");
            return;
        }
        String[] messages = new String[tasks.getSize() + 1];
        messages[0] = "Here's your task list for you: ";

        for (int i = 0; i < tasks.getSize(); i++) {
            messages[i + 1] = (i + 1) + ". " + tasks.getTask(i).toString();
        }
        printFormattedMessages(messages);
        printEasterAlignedRight("Glimpses of a Journey");
    }

    /**
     * Prints the list of tasks that match the keyword in the task list.
     * 
     * @param keyword The keyword to search for tasks.
     * @param matchedTasks The list of tasks that match the keyword.
     */
    public void printMatchedTaskList(String keyword, ArrayList<Task> matchedTasks) {
        if (matchedTasks.size() == 0) {
            printFormattedMessages(new String[] { "Sorry, no tasks found matching [" + keyword + "]." });
            printEasterAlignedRight("Out of Nothing");
            return;
        }

        int totalMatchCount = matchedTasks.size();
        String[] messages = new String[totalMatchCount + 1];

        messages[0] = "Here are the matching tasks for [" + keyword + "] in your list: ";

        for (int i = 0; i < totalMatchCount; i++) {
            messages[i + 1] = (i + 1) + ". " + matchedTasks.get(i).toString();
        }

        printFormattedMessages(messages);
        printEasterAlignedRight("Hidden Love");
    }

    /**
     * Prints a Exception message for invalid input with double lines.
     * 
     * @param exceptionMessage The description the invalid input
     * @param secondLine The second message to display, aligned to the right.
     */
    public void printFickleException(String exceptionMessage, String secondLine) {
        System.out.println("[Invalid Input]  " + exceptionMessage);
        printEasterAlignedRight(secondLine);
    }

    /**
     * Prints an error message when tasks cannot be loaded from or saved to a
     * file.
     *
     * @param exceptionMessage Description of the file operation failure.
     */
    public void printFickleException(String exceptionMessage) {
        System.out.println("[Load/Save Error]  " + exceptionMessage);
    }

    /**
     * Prints an error message
     * 
     * @param errorMessage The message description the Error
     */
    public void printError(String errorMessage) {
        System.out.println("[Error]  " + errorMessage);
    }

    /**
     * Prints a single line message with indentation. No lines are printed at
     * top or bottom.
     * 
     * @param message The message to be printed.
     */
    public void printSingleLineWithoutLine(String message) {
        indentMessage(message);
    }

    /**
     * Prints an array of messages with indentation. A top horizontal line is
     * printed before the messages. The bottom horizontal line is never printed.
     * 
     * @param messages An array of messages to be printed
     */
    private void printFormattedMessages(String[] messages) {
        indentLine();
        for (String msg : messages) {
            indentMessage(msg);
        }
    }

    /**
     * Prints a single easter egg line aligned to the right of horizontal line.
     *
     * @param easterEgg The message to print.
     */
    public void printEasterAlignedRight(String easterEgg) {
        indentMessage("");
        indentMessage("                                                                  ~~~  " + easterEgg);
        indentLine();
    }

    /**
     * Prints an indented message.
     * 
     * @param msg The message to be indented
     */
    private void indentMessage(String msg) {
        System.out.println(INDENTATION + "  " + msg);
    }

    /**
     * Prints an indented line.
     */
    private void indentLine() {
        System.out.println(INDENTATION + LINE);
    }
}
