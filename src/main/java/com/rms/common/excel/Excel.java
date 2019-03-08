package com.rms.common.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface Excel {
    int ColumnNum() default 0;

    int ColumnWidth() default 100;

    String PropertyName() default "";

    String ColumnTitle() default "";

    boolean AutoMergeRow() default false;

    int GroupByColumnNum() default -1;
}
