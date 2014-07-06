package com.springapp.mvc.service;

import com.springapp.mvc.json_protocol.JSONResponse;

/**
 * Created by Nik on 06.07.2014.
 */
public interface ResponseService
{
    public JSONResponse successResponse(Object data);

    public JSONResponse successResponse(Object data, Integer totalCount);

    public JSONResponse errorResponse(String errorCode, Object errorDetails);
}
