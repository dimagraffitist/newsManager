/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.testapp.database.impl;

import com.epam.testapp.database.connectionpool.ConnectionPool;
import com.epam.testapp.database.connectionpool.PoolException;
import com.epam.testapp.database.DAOAttributeName;
import com.epam.testapp.database.DAOException;
import com.epam.testapp.database.INewsDAO;
import com.epam.testapp.model.News;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dzmitry_Neviarovich
 */
public class NewsDAO extends INewsDAO {

    private ConnectionPool connectionPool;
        
    private static final String SQL_SELECT_FIND_ALL = "SELECT * FROM NEWS_DATA ORDER BY NEWS_DATE DESC";
    private static final String SQL_SELECT_FIND_BY_ID = "SELECT * FROM NEWS_DATA WHERE NEWS_ID = ?";
    private static final String SQL_INSERT_NEWS = "INSERT INTO NEWS_DATA (TITLE,NEWS_DATE,BRIEF,CONTENT) VALUES (?,?,?,?)";
    private static final String SQL_UPDATE_NEWS = "UPDATE NEWS_DATA SET NEWS_DATE=?,BRIEF=?,CONTENT=?,TITLE=? WHERE NEWS_ID=?";
    private static final String SQL_DELETE_NEWS_S = "DELETE FROM NEWS_DATA WHERE NEWS_ID IN (";
    private static final String SQL_DELETE_NEWS_C = ",";
    private static final String SQL_DELETE_NEWS_E = ")";
    
    private static final String ID = "NEWS_ID";
    
    @Override
    public List<News> getList() throws DAOException {

        Statement statement = null;
        News news = null;
        List<News> newsList = null;
        Connection connection = null;

        try {
            connection = connectionPool.getConnection();

            newsList = new ArrayList<>();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_FIND_ALL);

            while (resultSet.next()) {
                news = new News();
                news.setId(resultSet.getInt(DAOAttributeName.ID));
                news.setTitle(resultSet.getString(DAOAttributeName.TITLE));
                news.setDate(resultSet.getDate(DAOAttributeName.DATA));
                news.setBrief(resultSet.getString(DAOAttributeName.BRIEF));
                news.setContent(resultSet.getString(DAOAttributeName.CONTENT));
                newsList.add(news);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error occurred while executing query", ex);
        } catch (PoolException ex) {
            throw new DAOException("Problem with ConnectionPool", ex);
        } finally {
            closeSatement(statement);
            connectionPool.freeConnection(connection);
        }

        return newsList;
    }

    @Override
    public News save(News news) throws DAOException{
        
        News returnNews = null;
        if (news.getId() == 0) {
            returnNews = create(news);
        } else {
            returnNews = update(news);
        }
        return returnNews;
    }
    
    private News update(News news) throws DAOException{
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_NEWS);
            preparedStatement.setDate(1, new java.sql.Date(news.getDate().getTime()));
            preparedStatement.setString(2, news.getBrief());
            preparedStatement.setString(3, news.getContent());
            preparedStatement.setString(4, news.getTitle());
            preparedStatement.setInt(5, news.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            throw new DAOException("Error occurred while executing query", ex);
        } catch (PoolException ex) {
            throw new DAOException("Problem with ConnectionPool", ex);
        } finally {
            closeSatement(preparedStatement);
            connectionPool.freeConnection(connection);
        }
        return news;
    }
    
    private News create(News news) throws DAOException{
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            int result = 0;
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT_NEWS, new String[]{ ID });
            preparedStatement.setString(1, news.getTitle());
            preparedStatement.setDate(2, new java.sql.Date(news.getDate().getTime()));
            preparedStatement.setString(3, news.getBrief());
            preparedStatement.setString(4, news.getContent());
            preparedStatement.executeUpdate();
            
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
            news.setId(result);
        } catch (SQLException ex) {
            throw new DAOException("Error occurred while executing query", ex);
        } catch (PoolException ex) {
            throw new DAOException("Problem with ConnectionPool", ex);
        } finally {
            closeSatement(preparedStatement);
            connectionPool.freeConnection(connection);
        }
        return news;
    }

    @Override
    public boolean remove(int[] ids) throws DAOException {
        boolean complete = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = connectionPool.getConnection();
            StringBuilder sqlRequestDelete = new StringBuilder(SQL_DELETE_NEWS_S);
            
            for (int j=0 ; j < ids.length ; j++) {
                if (j == (ids.length - 1)) {
                    sqlRequestDelete.append(ids[j]);
                    sqlRequestDelete.append(SQL_DELETE_NEWS_E);
                } else {
                    sqlRequestDelete.append(ids[j]);
                    sqlRequestDelete.append(SQL_DELETE_NEWS_C);
                }
            }
            
            preparedStatement = connection.prepareStatement(sqlRequestDelete.toString());
            preparedStatement.executeUpdate();
            complete = true;
        } catch (SQLException ex) {
            throw new DAOException("Error occurred while executing query", ex);
        } catch (PoolException ex) {
            throw new DAOException("Problem with ConnectionPool", ex);
        } finally {
            closeSatement(preparedStatement);
            connectionPool.freeConnection(connection);
        }
        return complete;
    }

    @Override
    public News findById(int id) throws DAOException {

        PreparedStatement preparedStatement = null;
        News news = null;
        Connection connection = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(SQL_SELECT_FIND_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                news = new News();
                news.setId(resultSet.getInt(DAOAttributeName.ID));
                news.setTitle(resultSet.getString(DAOAttributeName.TITLE));
                news.setDate(resultSet.getDate(DAOAttributeName.DATA));
                news.setBrief(resultSet.getString(DAOAttributeName.BRIEF));
                news.setContent(resultSet.getString(DAOAttributeName.CONTENT));
            }
        } catch (SQLException ex) {
            throw new DAOException("Error occurred while executing query", ex);
        } catch (PoolException ex) {
            throw new DAOException("Problem with ConnectionPool", ex);
        } finally {
            closeSatement(preparedStatement);
            connectionPool.freeConnection(connection);
        }
        return news;
    }
    
    @Override
    public void setConnectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

}
