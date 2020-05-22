package br.com.cannoni.service1.exceptions;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author patrizio
 * @since 22/05/2020
 * 
 */
public class WriteFileExceptionTest {

    @Test
    public void instanceMessageMustBeNotNullTest() {
        final String message = "Erro generico na escrita do arquivo";
        final Throwable t = new IOException(message);
        
        Throwable wfe = new WriteFileException(t);
        String wfeMsg = wfe.getMessage();
        
        Assert.assertSame(message, wfeMsg);
    }
    
    @Test
    public void instanceMessageMustBeNullTest() {
        Throwable wfe = new WriteFileException(null);
        String wfeMsg = wfe.getMessage();
        
        Assert.assertNull(wfeMsg);
    }
    
}
