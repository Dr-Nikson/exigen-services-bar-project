package com.springapp.mvc.exceptions;

/**
 * Created by Nik on 06.07.2014.
 */
public class UserException extends Exception
{
    public UserException()
    {
        super();
    }

    public UserException(String message)
    {
        super(message);
    }

    public UserException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public UserException(Throwable cause)
    {
        super(cause);
    }
}
