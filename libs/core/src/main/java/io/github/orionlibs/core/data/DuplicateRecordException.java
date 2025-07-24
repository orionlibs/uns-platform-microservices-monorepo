package io.github.orionlibs.core.data;

import io.github.orionlibs.core.asserts.CheckedException;

public class DuplicateRecordException extends CheckedException
{
    private static final String DefaultErrorMessage = "There was an error.";


    public DuplicateRecordException(String errorMessage)
    {
        super(errorMessage);
    }


    public DuplicateRecordException(String errorMessage, Object... arguments)
    {
        super(String.format(errorMessage, arguments));
    }


    public DuplicateRecordException(Throwable cause, String errorMessage, Object... arguments)
    {
        super(String.format(errorMessage, arguments), cause);
    }


    public DuplicateRecordException(Throwable cause)
    {
        super(DefaultErrorMessage, cause);
    }
}