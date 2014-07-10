package com.springapp.mvc.exceptions;

/**
 * Created by Nik on 11.07.2014.
 */
public class BanquetOrderException extends Exception
{
    public BanquetOrderException()
    {
        super();
    }

    public BanquetOrderException(String message)
    {
        super(message);
    }

    public BanquetOrderException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public BanquetOrderException(Throwable cause)
    {
        super(cause);
    }
}
