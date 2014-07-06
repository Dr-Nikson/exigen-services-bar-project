package com.springapp.mvc.json_protocol;

/**
 * Created by Nik on 06.07.2014.
 */
public class JSONErrorResponse implements JSONResponse
{
    private ResponseStatus status;
    private String errorCode;
    private Object errorDetails;

    public JSONErrorResponse(String errorCode, Object errorDetails)
    {
        this.errorCode = errorCode;
        this.errorDetails = errorDetails;
        this.status = ResponseStatus.ERROR;
    }

    public ResponseStatus getStatus()
    {
        return status;
    }

    public String getErrorCode()
    {
        return errorCode;
    }

    public Object getErrorDetalis()
    {
        return errorDetails;
    }
}
