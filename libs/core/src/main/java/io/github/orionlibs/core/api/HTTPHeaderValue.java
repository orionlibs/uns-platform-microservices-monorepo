package io.github.orionlibs.core.api;

import io.github.orionlibs.core.OrionEnumeration;

public enum HTTPHeaderValue implements OrionEnumeration
{
    Bearer("Bearer ");
    private String name;


    private HTTPHeaderValue(String name)
    {
        setName(name);
    }


    @Override
    public String get()
    {
        return getName();
    }


    public String getName()
    {
        return this.name;
    }


    public void setName(String name)
    {
        this.name = name;
    }


    @Override
    public boolean is(OrionEnumeration other)
    {
        return other instanceof HTTPHeaderValue && this == other;
    }


    @Override
    public boolean isNot(OrionEnumeration other)
    {
        return other instanceof HTTPHeaderValue && this != other;
    }


    public static boolean valueExists(String other)
    {
        HTTPHeaderValue[] values = values();
        for(HTTPHeaderValue value : values)
        {
            if(value.get().equals(other))
            {
                return true;
            }
        }
        return false;
    }


    public static HTTPHeaderValue getEnumForValue(String other)
    {
        HTTPHeaderValue[] values = values();
        for(HTTPHeaderValue value : values)
        {
            if(value.get().equals(other))
            {
                return value;
            }
        }
        return null;
    }
}