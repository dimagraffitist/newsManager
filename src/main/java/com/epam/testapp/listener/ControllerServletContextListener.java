/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.testapp.listener;

import com.epam.testapp.utils.ConverterStringToDate;
import java.util.Date;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.log4j.Logger;

/**
 *  ServletContextListener is used to connect converter.
 * 
 */
public class ControllerServletContextListener implements ServletContextListener{

    private final static Logger logger = Logger.getLogger(ControllerServletContextListener.class);
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        
        Converter converterStringToDate = new ConverterStringToDate();
        ConvertUtils.register(converterStringToDate, Date.class);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
    
}
