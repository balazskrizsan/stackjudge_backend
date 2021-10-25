package com.kbalazsworks.stackjudge.domain_aspects.aspects;

import com.kbalazsworks.stackjudge.domain_aspects.enums.RedisCacheRepositorieEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RedisCacheByCompanyIdList
{
    RedisCacheRepositorieEnum repository();
}
