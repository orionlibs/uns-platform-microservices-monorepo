package io.github.orionlibs.user.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Converter
public class PasswordColumnConverter implements AttributeConverter<String, String>
{
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public String convertToDatabaseColumn(String s)
    {
        if(s == null)
        {
            return null;
        }
        return passwordEncoder.encode(s);
    }


    @Override
    public String convertToEntityAttribute(String s)
    {
        return "";
    }
}
