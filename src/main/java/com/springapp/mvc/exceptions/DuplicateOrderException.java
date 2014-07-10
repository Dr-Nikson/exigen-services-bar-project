package com.springapp.mvc.exceptions;

/**
 * Created by Nik on 11.07.2014.
 */
public class DuplicateOrderException extends Exception
{
    public DuplicateOrderException()
    {
        super();
    }

    public DuplicateOrderException(String message)
    {
        super(message);
    }

    public DuplicateOrderException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public DuplicateOrderException(Throwable cause)
    {
        super(cause);
    }
}
