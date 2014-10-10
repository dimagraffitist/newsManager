/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.testapp.presentation.form;

import com.epam.testapp.model.News;
import java.util.List;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author Dzmitry_Neviarovich
 */
public class NewsForm extends ActionForm{
    private News newsMessage;
    private List<News> newsList;

    public NewsForm(){
        newsMessage = new News();
    }

    public News getNewsMessage() {
        return newsMessage;
    }

    public void setNewsMessage(News newsMessage) {
        this.newsMessage = newsMessage;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }
    
}
