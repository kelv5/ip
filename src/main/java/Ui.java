import java.util.Scanner;

/**
 * UI class for handling user interactions.
 */
public class Ui {
    public static final String LINE = "___________________________________________________";
    private static final String INDENTATION = "    ";
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
        String logo = "     ______ _      _    _\n"
                + "    |  ___(_)    | |  | |     \n"
                + "    | |_   _  ___| | _| | ___ \n"
                + "    |  _| | |/ __| |/ / |/ _ \\\n"
                + "    | |   | | |__|   <|_|  __/\n"
                + "    \\_|   |_|\\___|_|\\_\\_|\\___|\n";
        System.out.println(logo);
    }

    /**
     * Prints Fickle's greeting message.
     */
    public void greet() {
        printFormattedMessages(new String[] { "Hello! I'm Fickle", "What shall we do today? " });
    }

    /**
     * Prints Fickle's goodbye message.
     */
    public void sayGoodbye() {
        printFormattedMessages(new String[] { "Goodbye! See you soon!" });
        scanner.close();
    }

    /**
     * Reads a line of user input.
     * 
     * @return The user input.
     */
    public String readInput() {
        System.out.println();
        System.out.print("Input: ");
        return scanner.nextLine();
    }

    /**
     * Echoes a single line of input.
     * 
     * @param input The input to echo.
     */
    public void echoLine(String input) {
        printFormattedMessages(new String[] { input });
    }

    /**
     * Prints messages with indentation and lines.
     * 
     * @param messages
     */
    private void printFormattedMessages(String[] messages) {
        indentLine();
        for (String msg : messages) {
            indentMessage(msg);
        }
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
