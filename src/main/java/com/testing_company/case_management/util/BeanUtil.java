package com.testing_company.case_management.util;


import java.lang.reflect.Field;
import java.util.Optional;
import java.util.function.Function;

public class BeanUtil {
    public static <T> void  copyNotNullProperties(T input, T update){
        for(Field field:input.getClass().getDeclaredFields()){
            field.setAccessible(true);
            try {
                Object value=field.get(input);
                if(value!=null){
                    field.set(update, value);
                }
            } catch (IllegalAccessException ignored){}
        }
    }
    public static <T, ID> T findIfIdPresent(ID id, Function<ID, Optional<T>> finder){
        return id!=null?finder.apply(id).orElse(null):null;
    }
}
