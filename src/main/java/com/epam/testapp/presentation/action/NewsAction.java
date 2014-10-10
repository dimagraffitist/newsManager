/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.testapp.presentation.action;

import com.epam.testapp.database.connectionpool.ConnectionPool;
import com.epam.testapp.database.DAOException;
import com.epam.testapp.database.INewsDAO;
import com.epam.testapp.model.News;
import com.epam.testapp.presentation.form.NewsForm;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;

/**
 *
 * @author Dzmitry_Neviarovich
 */
public class NewsAction extends LookupDispatchAction {
    
    private final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(NewsAction.class);

    private final static String NEWS_LIST = "news_list_page";
    private final static String NEWS_SAVE = "news_save_page";
    private final static String NEWS_VIEW = "news_view_page";
    private final static String ERROR_PAGE = "error_page";

    private final static String LAST_PAGE = "last_page";
    private final static String BACK_PAGE = "back_page";

    private final static String LANG = "lang";
    private final static String ID = "newsMessage.id";
    private final static String CHECKED_ID = "checkedID";

    private INewsDAO newsDAO;

    public void setNewsDao(INewsDAO newsDAO) {
        this.newsDAO = newsDAO;
    }

    /**
     * View News List page
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        request.getSession().setAttribute(BACK_PAGE, NEWS_LIST);
        request.getSession().setAttribute(LAST_PAGE, NEWS_LIST);
        NewsForm newsForm = (NewsForm) form;

        try {
            newsForm.setNewsList(newsDAO.getList());
        } catch (DAOException ex) {
            logger.error("Problem with DAO");
        }
        return mapping.findForward(NEWS_LIST);
    }

    /**
     * View News View page
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        request.getSession().setAttribute(BACK_PAGE, NEWS_VIEW);
        request.getSession().setAttribute(LAST_PAGE, NEWS_VIEW);
        
        String strId = request.getParameter(ID);
        News news = null;
        int id = 0;
        if (!strId.isEmpty()) {
            id = Integer.valueOf(strId);
        }
        
        try {
            news = newsDAO.findById(id);
        } catch (DAOException ex) {
            logger.error("Problem with DAO");
        }
        if (news == null) {
            return mapping.findForward(ERROR_PAGE);
        }
        NewsForm newsForm = (NewsForm) form;
        newsForm.setNewsMessage(news);
        return mapping.findForward(NEWS_VIEW);
    }

    /**
     * View News Edit page
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        request.getSession().setAttribute(LAST_PAGE, NEWS_SAVE);
        NewsForm newsForm = (NewsForm) form;
        String strId = request.getParameter(ID);
        int id = 0;
        if (!strId.isEmpty()) {
            id = Integer.valueOf(strId);
        }
        try {
            newsForm.setNewsMessage(newsDAO.findById(id));
        } catch (DAOException ex) {
            logger.error("Problem with DAO");
        }
        return mapping.findForward(NEWS_SAVE);
    }

    /**
     * Execute delete news
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        String[] checkedID = request.getParameterValues(CHECKED_ID);
        if (checkedID == null) {
            checkedID = request.getParameterValues(ID);
        }

        int[] checkedIDInt = new int[checkedID.length];
        for (int j = 0; j < checkedID.length; j++) {
            checkedIDInt[j] = Integer.valueOf(checkedID[j]);
        }

        try {
            newsDAO.remove(checkedIDInt);
        } catch (DAOException ex) {
            logger.error("Problem with DAO");
        }

        return list(mapping, form, request, response);
    }

    /**
     * View rollback page
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        String backPage = (String) request.getSession().getAttribute(BACK_PAGE);
        return mapping.findForward(backPage);
    }

    /**
     * Execute save news
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        request.getSession().setAttribute(LAST_PAGE, NEWS_SAVE);
        NewsForm newsForm = (NewsForm) form;
        String id = request.getParameter(ID);
        if (!id.equals("0")) {
            newsForm.getNewsMessage().setId(Integer.valueOf(id));
        }
        try {
            News news = newsDAO.save(newsForm.getNewsMessage());
            newsForm.setNewsMessage(news);
        } catch (DAOException ex) {
            logger.error("Problem with DAO");
        }

        return mapping.findForward(NEWS_VIEW);
    }

    /**
     * View create news page
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        request.getSession().setAttribute(BACK_PAGE, NEWS_LIST);
        request.getSession().setAttribute(LAST_PAGE, NEWS_SAVE);
        NewsForm newsForm = (NewsForm) form;
        if (newsForm.getNewsMessage() != null) {
            newsForm.getNewsMessage().setId(0);
            newsForm.getNewsMessage().setTitle("");
            newsForm.getNewsMessage().setDate(new Date());
            newsForm.getNewsMessage().setBrief("");
            newsForm.getNewsMessage().setContent("");
        }
        return mapping.findForward(NEWS_SAVE);
    }

    /**
     * Execute switch language
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward switchLang(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        Locale lang = new Locale((String) request.getParameter(LANG));
        request.getSession().setAttribute(Globals.LOCALE_KEY, lang);
        String lastPage = (String) request.getSession().getAttribute(LAST_PAGE);

        return mapping.findForward(lastPage);
    }
    

    @Override
    protected Map getKeyMethodMap() {
        Map keyMap = new HashMap();
        keyMap.put("list", "list");
        keyMap.put("view", "view");
        keyMap.put("edit", "edit");
        keyMap.put("delete", "delete");
        keyMap.put("cancel", "cancel");
        keyMap.put("save", "save");
        keyMap.put("add", "add");
        keyMap.put("switchLang", "switchLang");
        keyMap.put("news.view.submit.edit", "edit");
        keyMap.put("news.list.submit.delete", "delete");
        keyMap.put("news.save.submit.save", "save");
        keyMap.put("news.save.submit.cancel", "cancel");
        return keyMap;

    }
}
