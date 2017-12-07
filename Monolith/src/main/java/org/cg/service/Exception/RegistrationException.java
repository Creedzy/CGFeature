package org.cg.service.Exception;

public class RegistrationException extends Exception{

    /**
     * 
     */
    private static final long serialVersionUID = 2414L;
    
    public RegistrationException() {
    }

    public RegistrationException(String message) {
        super(message);
    }

    public RegistrationException(Throwable cause) {
        super(cause);
    }

    public RegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

}
