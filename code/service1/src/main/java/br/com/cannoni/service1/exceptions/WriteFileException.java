package br.com.cannoni.service1.exceptions;

/**
 * @author patrizio
 * @since 18/04/2019
 * 
 */
public class WriteFileException extends RuntimeException {

    private static final long serialVersionUID = 6372258470988615694L;

    public WriteFileException(final Throwable t) {
        super(t);
    }

    @Override
    public String getMessage() {
        Throwable cause = super.getCause();
        return cause != null ? cause.getMessage() : super.getMessage();
    }

}
