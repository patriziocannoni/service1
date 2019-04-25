package br.com.cannoni.service1.exceptions;

/**
 * @author patrizio
 * @since 18/04/2019
 * 
 */
public class WriteFileException extends RuntimeException {
    
    private static final long serialVersionUID = 6372258470988615694L;

    public WriteFileException(Throwable t)  {
        super(t);
    }
    
}
