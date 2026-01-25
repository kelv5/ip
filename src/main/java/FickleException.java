public class FickleException extends Exception {
    private String secondLine;

    public FickleException(String exceptionMessage) {
        super(exceptionMessage);
        this.secondLine = null;
    }

    public FickleException(String exceptionMessage, String secondLine) {
        super(exceptionMessage);
        this.secondLine = secondLine;
    }

    public boolean hasSecondLine() {
        return !(secondLine == null);
    }

    public String getSecondLine() {
        return secondLine;
    }
}
