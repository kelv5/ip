/**
 * Fickle chatbot main class.
 */
public class Fickle {
    private Ui ui;

    /**
     * Constructor for Fickle.
     */
    public Fickle() {
        this.ui = new Ui();
    }

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
        ui.printLogo();
        ui.greet();

        while (true) {
            String input = ui.readInput();
            if (input.equalsIgnoreCase("bye")) {
                break;
            }
            ui.echoLine(input);
        }
        ui.sayGoodbye();
    }
}