package com.springapp.mvc.json_protocol;

import java.util.Collection;

/**
 * Created by Nik on 06.07.2014.
 */
public class JSONSuccessResponse implements JSONResponse
{
    private ResponseStatus status;
    private Integer dataCount;
    private Integer totalCount;
    private Object data;


    public JSONSuccessResponse(Object data)
    {
        if (data instanceof Collection<?>)
        {
            this.initResponse(data, ((Collection) data).size(), ((Collection) data).size());
        }
        else
        {
            this.initResponse(data, 1, 1);
        }
    }

    public JSONSuccessResponse(Object data, Integer totalCount)
    {
        if (data instanceof Collection<?>)
        {
            this.initResponse(data, ((Collection) data).size(), totalCount);
        }
        else
        {
            this.initResponse(data, 1, totalCount);
        }
    }

    protected void initResponse(Object data, Integer dataCount, Integer totalCount)
    {
        this.status = ResponseStatus.SUCCESS;
        this.data = data;
        this.dataCount = dataCount;
        this.totalCount = totalCount;
    }

    public ResponseStatus getStatus()
    {
        return status;
    }

    public Object getData()
    {
        return data;
    }

    public Integer getDataCount()
    {
        return dataCount;
    }

    public Integer getTotalCount()
    {
        return totalCount;
    }
}
