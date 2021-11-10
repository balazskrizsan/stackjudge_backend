package com.kbalazsworks.stackjudge.helpers;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ObjectComparatorService
{
    private static final Logger log = LoggerFactory.getLogger(ObjectComparatorService.class);

    public <L, R> boolean byValues(L left, R right)
    {
        boolean isOk = true;

        for (Field testedField : getAllPublicAndPrivateObjectPropertyName(left))
        {
            String fqn = "N/A";

            try
            {
                Class<?> clazz = left.getClass();
                Field    f     = clazz.getDeclaredField(testedField.getName());
                f.setAccessible(true);
                Object leftField  = f.get(left);
                Object rightField = f.get(right);
                fqn = clazz.getName() + " % " + f.getName();

                if (null == leftField)
                {
                    if (null != rightField)
                    {
                        printError(fqn, leftField, rightField);

                        isOk = false;
                    }

                    continue;
                }

                if (null == rightField)
                {
                    printError(fqn, leftField, rightField);

                    isOk = false;

                    continue;
                }

                if (skipRecursiveCompare(leftField))
                {
                    if (!leftField.equals(rightField))
                    {
                        printError(fqn, leftField, rightField);

                        isOk = false;
                    }

                    continue;
                }

                if (!byValues(leftField, rightField))
                {
                    isOk = false;
                }
            }
            catch (NoSuchFieldException | IllegalAccessException e)
            {
                printError(fqn, e);

                isOk = false;
            }
        }

        return isOk;
    }

    // @todo: check from package
    private boolean skipRecursiveCompare(Object var)
    {
        return var instanceof Boolean
            || var instanceof Short
            || var instanceof Long
            || var instanceof Integer
            || var instanceof Double
            || var instanceof Float
            || var instanceof String
            || var instanceof Character
            || var instanceof LocalDate
            || var instanceof LocalDateTime
            || var instanceof DateTime
            || var instanceof Date
            ;
    }

    private void printError(String fqn, Throwable e)
    {
        System.out.println("********** Compare error **********");
        System.out.println("fqn: " + fqn);
        System.out.println("Exception: ");
        e.printStackTrace();
    }

    private void printError(String fqn, Object left, Object right)
    {
        System.out.println("********** Compare error **********");
        System.out.println("fqn: " + fqn);
        System.out.println("Left: " + (null == left ? "null" : left.toString()));
        System.out.println("Right: " + (null == right ? "null" : right.toString()));
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
