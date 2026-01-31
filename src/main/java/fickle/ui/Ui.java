package fickle.ui;

import fickle.tasks.TaskList;
import java.util.Scanner;

/**
 * UI class for handling user interactions.
 */
public class Ui {
    public static final String LINE = "____________________________________________________________________________________________";
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
        String logo = "       ______ _      _    _\n"
                + "      |  ___(_)    | |  | |     \n"
                + "      | |_   _  ___| | _| | ___ \n"
                + "      |  _| | |/ __| |/ / |/ _ \\\n"
                + "      | |   | | |__|   <|_|  __/\n"
                + "      \\_|   |_|\\___|_|\\_\\_|\\___|\n";
        System.out.println(logo);
    }

    /**
     * Prints Fickle's greeting message.
     */
    public void greet() {
        printFormattedMessages(new String[] { "Hi! I'm Fickle", "What feels right to start with today? " }, true);
    }

    /**
     * Prints Fickle's goodbye message.
     */
    public void sayGoodbye() {
        printFormattedMessages(new String[] { "Goodbye. We'll come back to this." }, false);
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
     * @param taskname   The name of the added task.
     * @param totalTasks Total number of tasks after added a new task
     * 
     */
    public void printAddedTask(String taskname, int totalTasks) {
        String totalTasksMessage = "Now you have " + totalTasks + " task" + (totalTasks == 1 ? "" : "s")
                + " in the list.";
        printFormattedMessages(
                new String[] { "Got it. I've added this task: ", "  " + taskname, "\n",
                        totalTasksMessage },
                true);
    }

    /**
     * Prints the deleted task message.
     * 
     * @param taskname   The name of the deleted task.
     * @param totalTasks Total number of tasks after deleted a new task
     * 
     */
    public void printDeletedTask(String taskname, int totalTasks) {
        String totalTasksMessage = "Now you have " + totalTasks + " task" + (totalTasks == 1 ? "" : "s")
                + " in the list.";
        printFormattedMessages(
                new String[] { "Noted. I've removed this task: ", taskname, "\n",
                        totalTasksMessage },
                true);
    }

    /**
     * Prints the marked task message.
     * 
     * @param taskname The name of the marked task.
     */
    public void printMarkedTask(String taskname) {
        printFormattedMessages(
                new String[] { "All set. This task is marked as done: ", taskname }, false);
        printEasterAlignedRight("One After Another");
    }

    /**
     * Prints the unmarked task message.
     * 
     * @param taskname The name of the unmarked task.
     */
    public void printUnmarkedTask(String taskname) {
        printFormattedMessages(new String[] { "Noted. This task is now unmarked: ", taskname },
                false);
        printEasterAlignedRight("Pace Yourself");
    }

    /**
     * Prints the list of tasks.
     * 
     * @param tasks The tasklist to be printed.
     */
    public void printTaskList(TaskList tasks) {
        if (tasks.getSize() == 0) {
            printFormattedMessages(new String[] { "No tasks remaining. " }, false);
            printEasterAlignedRight("A Little Happiness");
            return;
        }
        String[] messageList = new String[tasks.getSize() + 1];
        messageList[0] = "Here's your task list for you: ";

        for (int i = 0; i < tasks.getSize(); i++) {
            messageList[i + 1] = (i + 1) + ". " + tasks.getTask(i).toString();
        }
        // add in the print easter egg a glimpse of journey
        printFormattedMessages(messageList, false);
        printEasterAlignedRight("Glimpses of a Journey");
    }

    /**
     * Prints a Exception message for invalid input with double lines.
     * 
     * @param exceptionMessage The description the invalid input
     * @param secondLine       The second message to display, aligned to the right.
     */
    public void printInvalidInput(String exceptionMessage, String secondLine) {
        System.out.println("[Invalid Input]  " + exceptionMessage);
        printEasterAlignedRight(secondLine);
    }

    /**
     * Prints a Exception message for invalid input with a single line.
     *
     * @param exceptionMessage The description of the invalid input.
     */
    public void printInvalidInput(String exceptionMessage) {
        System.out.println("[Invalid Input]  " + exceptionMessage);
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
     * Prints a single line message with indentation
     * No lines are printed
     * 
     * @param message The message to be printed.
     */
    public void printSingleLineWithoutLine(String message) {
        indentMessage(message);
    }

    /**
     * Prints an array of messages with indentation
     * A top horizontal line is always printed.
     * The bottom horizontal line is printed only if hasBottomLine is true.
     * 
     * @param messages      An array of messages to be printed
     * @param hasBottomLine Print a bottom horizontal line if true
     * 
     */
    private void printFormattedMessages(String[] messages, boolean hasBottomLine) {
        indentLine();
        for (String msg : messages) {
            indentMessage(msg);
        }
        if (hasBottomLine) {
            indentLine();
        }
    }

    /**
     * Prints a single easter egg line aligned to the right of horizontal line.
     *
     * @param easterEgg The message to print.
     */
    public void printEasterAlignedRight(String easterEgg) {
        indentMessage("");
        indentMessage("                                                           ~~~  " + easterEgg);
        indentLine();
    }

    /**
     * Prints an indented message.
     * 
     * @param msg
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