package agentie.service;

public class AgentException extends Exception {
    public AgentException() {
        super();
    }

    public AgentException(String message) {
        super(message);
    }

    public AgentException(String message, Throwable cause) {
        super(message, cause);
    }
}
