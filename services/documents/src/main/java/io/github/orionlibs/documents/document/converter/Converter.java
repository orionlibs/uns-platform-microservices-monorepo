package io.github.orionlibs.documents.document.converter;

/**
 * It converts a data type to another
 * @param <FROM>
 * @param <TO>
 */
public interface Converter<FROM, TO>
{
    TO convert(FROM fromType);
}
