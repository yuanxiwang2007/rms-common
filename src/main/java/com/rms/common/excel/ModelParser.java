package com.rms.common.excel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

public class ModelParser {

    public static <T> Hashtable<Integer, Excel> GetColumnMappingInfo(Class<T> modelType) throws Exception {
        Hashtable<Integer, Excel> dic = null;

        Field[] properties = modelType.getDeclaredFields();
        if (properties != null && properties.length > 0)
        {
            for (Field property : properties)
            {
                Excel atts = property.getAnnotation(Excel.class);
                if (atts != null)
                {
                    Excel mappingInfo = atts;

                    if (dic == null)
                    {
                        dic = new Hashtable<>();
                    }

                    if (dic.get(mappingInfo.ColumnNum())==null)
                    {
                        //mappingInfo.PropertyName = property.getName();
                        changeAnnotationValue(mappingInfo,"PropertyName",property.getName());
                        dic.put(mappingInfo.ColumnNum(), mappingInfo);
                    }
                }
            }
        }

        return dic;
    }
    public static Object changeAnnotationValue(Annotation annotation, String key, Object newValue) throws Exception{
        InvocationHandler handler = Proxy.getInvocationHandler(annotation);
        Field f;
        try {
            f = handler.getClass().getDeclaredField("memberValues");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
        f.setAccessible(true);
        Map<String, Object> memberValues;
        memberValues = (Map<String, Object>) f.get(handler);
        Object oldValue = memberValues.get(key);
        if(oldValue == null || oldValue.getClass() != newValue.getClass()){
            throw new IllegalArgumentException();
        }
        memberValues.put(key, newValue);
        return oldValue;
    }
}
