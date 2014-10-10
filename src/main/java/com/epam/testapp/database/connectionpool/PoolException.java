/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epam.testapp.database.connectionpool;

import com.epam.testapp.exception.ProjectException;

/**
 * Exception that provides information about error in Connection Pool
 * 
 */
public class PoolException extends ProjectException{
    
    private static final long serialVersionUID = 1L;
    
    /**
     *  PoolException using given message and given hidden exception
     * @param msg The message explaining the reason for the exception 
     */
    public PoolException(String msg) {
        super(msg);
    }

    /**
     *  PoolException using given message
     * @param msg The message explaining the reason for the exception 
     * @param e exception that will be wrapped
     */
    public PoolException(String msg,Exception e){
        super(msg,e);
    }
}
