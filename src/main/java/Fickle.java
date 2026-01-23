/**
 * Fickle chatbot main class.
 */
public class Fickle {
    public static final String LINE = "___________________________________________________";

    /**
     * The entry point for the Fickle chatbot.
     * 
     * @param args
     */
    public static void main(String[] args) {
        Fickle fickle = new Fickle();
        fickle.run();
    }

    /**
     * Runs the Fickle chatbot
     * Displays the logo, greeting, and goodbye message.
     */
    public void run() {
        printLogo();
        greet();
        sayGoodbye();
    }

    /**
     * Displays the ASCII art logo.
     */
    private void printLogo() {
        System.out.println(LINE);
        String logo = "______ _      _    _\n"
                + "|  ___(_)    | |  | |     \n"
                + "| |_   _  ___| | _| | ___ \n"
                + "|  _| | |/ __| |/ / |/ _ \\\n"
                + "| |   | | |__|   <| |  __/\n"
                + "\\_|   |_|\\___|_|\\_\\_|\\___|\n";
        System.out.println(logo);
    }

    /**
     * Prints Fickle's greeting message.
     */
    private void greet() {
        System.out.println(" Hello! I'm Fickle");
        System.out.println(" What shall we do today? ");
        System.out.println(LINE);
    }

    /**
     * Prints Fickle's goodbye message.
     */
    private void sayGoodbye() {
        System.out.println(" Bye. See you soon!");
        System.out.println(LINE);
    }
}