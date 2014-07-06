package com.springapp.mvc.exceptions;

/**
 * Created by Nik on 06.07.2014.
 */
public class OrderException extends Exception
{
    public OrderException(Throwable cause)
    {
        super(cause);
    }

    public OrderException(String message)
    {
        super(message);
    }

    public OrderException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public OrderException()
    {
        super();
    }
}
