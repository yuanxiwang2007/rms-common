package com.rms.common.excel;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface Excel {
    int ColumnNum() default  0;

    int ColumnWidth() default  100;

    String PropertyName() default   "";

    String ColumnTitle() default "";
}
