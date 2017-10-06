/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.utils;

import eu.squadd.batch.domain.exceptions.ContentTooLongException;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.batch.item.file.transform.Alignment;
import org.springframework.batch.item.file.transform.LineAggregator;

/**
 *
 * @author smorcja
 * @param <T>
 */
public class FixedLengthLineAggregator<T> implements LineAggregator<T> {

    private final Class<T> payloadClass;
    private final Map<String, Integer> fieldsDefinition;
        
    public FixedLengthLineAggregator(Class<T> payloadClass, Map<String, Integer> fieldsDefinition) {
        this.payloadClass = payloadClass;
        this.fieldsDefinition = fieldsDefinition;
    }

    @Override
    public String aggregate(T t) {
        StringBuilder line = new StringBuilder();
        Field[] declaredFields = payloadClass.getDeclaredFields();
        for (Field field : declaredFields) {
            try {
                String strValue = null;
                char padding = '0';
                Alignment alignment = Alignment.RIGHT;
                Object value = new PropertyDescriptor(field.getName(), payloadClass).getReadMethod().invoke(t);
                if (value != null) {
                    //check only types we can have in our input files
                    if (value.getClass() == String.class) {
                        strValue = (String) value;
                        padding = ' ';
                        alignment = Alignment.LEFT;
                    } else {
                        if (value.getClass() == java.lang.Integer.class) strValue = Integer.toString((Integer) value);
                        else if (value.getClass() == Long.class) strValue = Long.toString((Long) value);
                        else if (value.getClass() == java.lang.Short.class) strValue = Short.toString((Short) value);
                        else if (value.getClass() == java.lang.Double.class) strValue = Double.toString((Double) value);
                        else 
                            throw new IllegalAccessException("Unrecognized field type"); //that should never happen actually                        
                    }
                }    
                if (fieldsDefinition.containsKey(field.getName())) {
                    StringBuilder sb = new StringBuilder();
                    int fieldLength = fieldsDefinition.get(field.getName());
                    char[] pad = new char[fieldLength];
                    Arrays.fill(pad, padding);
                    sb.append(pad);
                    if (strValue!=null) {
                        int start=0;
                        if (alignment == Alignment.RIGHT) {                            
                            start += (fieldLength - strValue.length());
                        } else if (alignment == Alignment.CENTER) {
                            start += ((fieldLength - strValue.length()) / 2);
                        }
                        if (start < 0) {
                            throw new ContentTooLongException("Contents to long to fit defined output field");
                        }
                        sb.replace(start, start + strValue.length(), strValue);
                    }
                    line.append(sb.toString());
                }
            } catch (IllegalAccessException | InvocationTargetException | IntrospectionException | ContentTooLongException ex) {
                Logger.getLogger(FixedLengthLineAggregator.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
        return line.toString();
    }    
}
