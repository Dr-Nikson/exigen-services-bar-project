package com.springapp.mvc.service;

import com.springapp.mvc.json_protocol.JSONErrorResponse;
import com.springapp.mvc.json_protocol.JSONResponse;
import com.springapp.mvc.json_protocol.JSONSuccessResponse;


/**
 * Created by Nik on 06.07.2014.
 */
public class ResponseServiceImpl implements ResponseService
{

    @Override
    public JSONResponse successResponse(Object data)
    {
        return new JSONSuccessResponse(data);
    }

    @Override
    public JSONResponse successResponse(Object data, Integer totalCount)
    {
        return new JSONSuccessResponse(data, totalCount);
    }

    @Override
    public JSONResponse errorResponse(String errorCode, Object errorDetails)
    {
        return new JSONErrorResponse(errorCode, errorDetails);
    }
}
