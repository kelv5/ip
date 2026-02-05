package fickle.exceptions;

/**
 * Exception class for errors specific to the Fickle chatbot. Could include a
 * second line of message for display.
 */
public class FickleException extends Exception {
    private String secondLine;

    /**
     * Constructor for FickleException with a single message.
     *
     * @param exceptionMessage The exception message.
     */
    public FickleException(String exceptionMessage) {
        super(exceptionMessage);
        this.secondLine = null;
    }

    /**
     * Constructor for FickleException with a main Exception message and an
     * optional second line.
     *
     * @param exceptionMessage The main exception message.
     * @param secondLine An optional second line to display.
     */
    public FickleException(String exceptionMessage, String secondLine) {
        super(exceptionMessage);
        this.secondLine = secondLine;
    }

    /**
     * Checks if this exception has a second line.
     *
     * @return True if there is a second line, false otherwise.
     */
    public boolean hasSecondLine() {
        return !(secondLine == null);
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
