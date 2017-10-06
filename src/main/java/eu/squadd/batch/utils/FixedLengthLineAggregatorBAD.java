/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import org.springframework.batch.item.file.transform.Alignment;
import org.springframework.batch.item.file.transform.DefaultFieldSet;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.batch.item.file.transform.Range;

/**
 *
 * @author smorcja
 * @param <T>
 */
public class FixedLengthLineAggregatorBAD<T> implements LineAggregator<T> {

    private final Class<T> payloadClass;
    private Range[] fieldRange;
    private int lastColumn;
    private Alignment align = Alignment.LEFT;
    private char padding = ' ';

    public FixedLengthLineAggregatorBAD(Class<T> payloadClass) {
        this.payloadClass = payloadClass;
    } 
    
    public void setColumns(Range[] columns) {
        lastColumn = findLastColumn(columns);
        this.fieldRange = columns;
    }

    public void setAlignment(Alignment alignment) {
        this.align = alignment;
    }

    public void setPadding(char padding) {
        this.padding = padding;
    }

    private int findLastColumn(Range[] columns) {
        int lastOffset = 1;
        int lastIndex = 0;
        for (int i = 0; i < columns.length; i++) {
            if (columns[i].getMin() > lastOffset) {
                lastOffset = columns[i].getMin();
                lastIndex = i;
            }
        }
        return lastIndex;
    }
    
    @Override
    public String aggregate(Object o) {
        String[] names = new String[payloadClass.getDeclaredFields().length];
        String[] values = new String[payloadClass.getDeclaredFields().length];
        Field[] fields = payloadClass.getDeclaredFields();
        int i=0;
        for (Field field : fields) {
            names[i] = field.getName();
            try {
                Object value = field.get(o);
                if (value != null) {
                    // that's only types we may have in our input files                        
                    if (value.getClass().isPrimitive()) {
                        if (value.getClass() == String.class) values[i] = (String) value;
                        else if (value.getClass() == java.lang.Integer.class) values[i] = Integer.toString((Integer) value);
                        else if (value.getClass() == Long.class) values[i] = Long.toString((Long) value);
                        else if (value.getClass() == java.lang.Short.class) values[i] = Short.toString((Short) value);
                        else if (value.getClass() == java.lang.Double.class) values[i] = Double.toString((Double) value);                        
                    } else {
                        //that should never happen actually
                    }
                }
            } catch (IllegalAccessException e) {
                //buffer.append(e.getMessage());
            }
        }
        FieldSet fieldSet = new DefaultFieldSet(names, values);
        return this.aggregate(fieldSet);
    }

    protected String aggregate(FieldSet fieldSet) {
        //Assert.notNull(fieldSet);
        //Assert.notNull(ranges);

        String[] args = fieldSet.getValues();

        //Assert.isTrue(args.length <= ranges.length, "Number of arguments must match number of fields in a record");

        // calculate line length
        int lineLength = fieldRange[lastColumn].hasMaxValue() ? fieldRange[lastColumn].getMax() : fieldRange[lastColumn].getMin() + args[lastColumn].length() - 1;

        // create stringBuffer with length of line filled with padding
        // characters
        char[] emptyLine = new char[lineLength];
        Arrays.fill(emptyLine, padding);
        StringBuilder stringBuilder = new StringBuilder(lineLength);
        stringBuilder.append(emptyLine);

        // aggregate all strings
        for (int i = 0; i < args.length; i++) {

            // offset where text will be inserted
            int start = fieldRange[i].getMin() - 1;

            // calculate column length
            int columnLength;
            if ((i == lastColumn) && (!fieldRange[lastColumn].hasMaxValue())) {
                columnLength = args[lastColumn].length();
            } else {
                columnLength = fieldRange[i].getMax() - fieldRange[i].getMin() + 1;

                String textToInsert = (args[i] == null) ? "" : args[i];
                //Assert.isTrue(columnLength >= textToInsert.length(), "Supplied text: " 
                //+textToInsert + " is longer than defined length: " + columnLength
                //);

		if (align == Alignment.RIGHT) {
                    start += (columnLength - textToInsert.length());
                } else if (align == Alignment.CENTER) {
                    start += ((columnLength - textToInsert.length()) / 2);
                }
                stringBuilder.replace(start, start + textToInsert.length(), textToInsert);
            }            
        }
        return stringBuilder.toString();
    }
}