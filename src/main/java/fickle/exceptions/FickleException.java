package fickle.exceptions;

/**
 * Exception class for errors specific to the Fickle chatbot. Could include a
 * second line of message for display.
 */
public class FickleException extends Exception {
    private String secondLine;

    /**
     * Constructor for FickleException with a single main message.
     * This constructor is only used for exceptions thrown during storage loading.
     *
     * @param exceptionMessage The main exception message.
     */
    public FickleException(String exceptionMessage) {
        super(exceptionMessage);
        this.secondLine = null;
    }

    /**
     * Constructor for FickleException with a main message and a second line.
     * This constructor is used for all other Fickle operations except storage loading.
     *
     * @param exceptionMessage The main exception message.
     * @param secondLine An second line to display.
     */
    public FickleException(String exceptionMessage, String secondLine) {
        super(exceptionMessage);
        this.secondLine = secondLine;
    }

    /**
     * Returns the second line of this exception.
     *
     * @return The second line message, or null if none.
     */
    public String getSecondLine() {
        return secondLine;
    }
}
