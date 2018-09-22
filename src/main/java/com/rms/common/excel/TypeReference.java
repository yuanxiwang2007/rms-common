package com.rms.common.excel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TypeReference<T> {
    private final Type rawType = this.getSuperclassTypeParameter(this.getClass());

    protected TypeReference() {
    }

    Type getSuperclassTypeParameter(Class<?> clazz) {
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (genericSuperclass instanceof Class) {
            if (org.apache.ibatis.type.TypeReference.class != genericSuperclass) {
                return this.getSuperclassTypeParameter(clazz.getSuperclass());
            } else {
                throw new TypeException("'" + this.getClass() + "' extends TypeReference but misses the type parameter. Remove the extension or add a type parameter to it.");
            }
        } else {
            Type rawType = ((ParameterizedType)genericSuperclass).getActualTypeArguments()[0];
            if (rawType instanceof ParameterizedType) {
                rawType = ((ParameterizedType)rawType).getRawType();
            }

            return rawType;
        }
    }

    public final Type getRawType() {
        return this.rawType;
    }

    public String toString() {
        return this.rawType.toString();
    }
}

