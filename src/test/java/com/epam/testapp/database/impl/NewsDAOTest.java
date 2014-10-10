/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.testapp.database.impl;

import com.epam.testapp.database.connectionpool.ConnectionPool;
import com.epam.testapp.database.connectionpool.PoolException;
import com.epam.testapp.model.News;
import java.util.List;
import java.util.ResourceBundle;
import javax.sql.DataSource;
import static junit.framework.Assert.assertEquals;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author Dzmitry_Neviarovich
 */
public class NewsDAOTest {
        
    private final static String DATABASE_CONFIG = "properties.database_config_test";
    private final static String URL = "database.url";
    private final static String USER = "database.user";
    private final static String PASSWORD = "database.password";
    
    private final static String NEWS_TITLE = "newsTitle";
    private final static String NEWS_TITLE_INSERT = "newsTitle";    

    private final static int NEWS_LIST_SIZE = 2;
    
    private NewsDAO newsDAO;
    
    public NewsDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws PoolException {
        ResourceBundle resource = ResourceBundle.getBundle(DATABASE_CONFIG);
        String url = resource.getString(URL);
        String user = resource.getString(USER);
        String password = resource.getString(PASSWORD);
        DataSource dataSource = new DriverManagerDataSource(url, user, password);
        ConnectionPool connectionPool = new ConnectionPool(dataSource);
        newsDAO = new NewsDAO();
        newsDAO.setConnectionPool(connectionPool);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getList method, of class NewsDAO.
     */
    @Test
    public void testGetList() throws Exception {

        List<News> result = newsDAO.getList();
        assertEquals(result.size(), NEWS_LIST_SIZE);

    }

    @Test
    public void testInsert() throws Exception {

        News news = newsDAO.findById(1);
        news.setId(0);
        news.setTitle(NEWS_TITLE_INSERT);
        News newInsertNews = newsDAO.save(news);
        int newsId = newInsertNews.getId();
        assertEquals(news.getTitle(), newInsertNews.getTitle());
        
        int[] ids = { newsId };
        newsDAO.remove(ids);
    }

    /**
     * Test of save method, of class NewsDAO.
     */
    @Test
    public void testSave() throws Exception {

        News newNews = newsDAO.findById(1);
        newNews.setTitle(NEWS_TITLE);
        newsDAO.save(newNews);
        News newSaveNews = newsDAO.findById(1);
        assertEquals(newSaveNews.getId(), newNews.getId());
        
    }

    /**
     * Test of remove method, of class NewsDAO.
     */
    @Test
    public void testRemove() throws Exception {
        
        News news = newsDAO.findById(1);
        news.setId(0);
        news.setTitle(NEWS_TITLE_INSERT);
        News newInsertNews = newsDAO.save(news);
        int newsId = newInsertNews.getId();
        
        int[] ids = { newsId };
        boolean expResult = true;
        boolean result = newsDAO.remove(ids);
        assertEquals(expResult, result);
                
    }

    /**
     * Test of findById method, of class NewsDAO.
     */
    @Test
    public void testFindById() throws Exception {

        News result = newsDAO.findById(1);
        assertEquals(NEWS_TITLE, result.getTitle());
    }

}
