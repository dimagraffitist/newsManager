/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.testapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.commons.beanutils.Converter;

/**
 * ConverterStringToDate is used to converting string to date.
 * @author Dima
 */
public class ConverterStringToDate implements Converter{
    
    private final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ConverterStringToDate.class);
    
    private final static String DATE_FORMAT = "MM/dd/yyyy";
    
    @Override
    public Date convert(Class type, Object o) {
        String dateStr = (String) o;
        Date date = null;
        
        try {
            date = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH).parse(dateStr);
        } catch (ParseException ex) {
            logger.error("Date is not converted");
        }
        return date;
    }
    
}
