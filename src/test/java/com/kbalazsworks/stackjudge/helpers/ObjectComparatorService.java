package com.kbalazsworks.stackjudge.helpers;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ObjectComparatorService
{
    public <L, R> boolean byValues(L left, R right)
    {
        try
        {
            for (Field leftField : getAllPublicAndPrivateObjectPropertyName(left))
            {
                Field f = left.getClass().getDeclaredField(leftField.getName());
                f.setAccessible(true);

                if (!f.get(left).equals(f.get(right)))
                {
                    return false;
                }
            }
        }
        catch (NoSuchFieldException | IllegalAccessException e)
        {
            return false;
        }

        return true;
    }

    private <T> List<Field> getAllPublicAndPrivateObjectPropertyName(T obj)
    {
        List<Field> publicStaticList = new ArrayList<>();

        for (Field field : obj.getClass().getDeclaredFields())
        {
            if (Modifier.isPublic(field.getModifiers()) || Modifier.isPrivate(field.getModifiers()))
            {
                publicStaticList.add(field);
            }
        }

        return publicStaticList;
    }
}
