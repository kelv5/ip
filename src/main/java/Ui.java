import java.util.Scanner;

/**
 * UI class for handling user interactions.
 */
public class Ui {
    public static final String LINE = "___________________________________________________";
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
        System.out.println(LINE);
        String logo = "  ______ _      _    _\n"
                + " |  ___(_)    | |  | |     \n"
                + " | |_   _  ___| | _| | ___ \n"
                + " |  _| | |/ __| |/ / |/ _ \\\n"
                + " | |   | | |__|   <|_|  __/\n"
                + " \\_|   |_|\\___|_|\\_\\_|\\___|\n";
        System.out.println(logo);
    }

    /**
     * Prints Fickle's greeting message.
     */
    public void greet() {
        System.out.println("    Hello! I'm Fickle");
        System.out.println("    What shall we do today? ");
        System.out.println(LINE);
    }

    /**
     * Prints Fickle's goodbye message.
     */
    public void sayGoodbye() {
        System.out.println("    Bye. See you soon!");
        System.out.println(LINE);
        scanner.close();
    }

    /**
     * Reads a line of user input.
     * 
     * @return The user input.
     */
    public String readInput() {
        return scanner.nextLine();
    }

    /**
     * Echoes the user input back.
     * 
     * @param input The user input.
     */
    public void echoLine(String input) {
        System.out.println("    You said: " + input);
        System.out.println(LINE);
    }

}
