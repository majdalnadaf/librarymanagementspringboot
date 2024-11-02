package com.librarymanagement.librarymanagement.api;

import lombok.Getter;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ApiResponse<T>
{

    private final String statusCode;
    private Map<String, String> errors = new HashMap<>();
    private  T data;

    public ApiResponse(String statusCode , Map<String,String> errors ,T data)
    {
        this.statusCode = statusCode;
        this.errors = errors;
        this.data = data;

    }

    public void AddError(String key , String value)
    {
          errors.put(key , value);
    }

}

