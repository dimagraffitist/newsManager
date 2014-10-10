/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.testapp.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Dzmitry_Neviarovich
 */
public class News implements Serializable{
    private int id;
    private String title;
    private Date date;
    private String brief;
    private String content;

    public News() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {        
        return date;
    }
 
    public void setDate(Date date) {
        this.date = date;
    }
 
    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
