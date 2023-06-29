package br.com.cannoni.service1.exceptions;

public class WriteFileException extends RuntimeException {

    public WriteFileException(final Throwable t) {
        super(t);
    }

    @Override
    public String getMessage() {
        Throwable cause = super.getCause();
        return cause != null ? cause.getMessage() : super.getMessage();
    }

}
