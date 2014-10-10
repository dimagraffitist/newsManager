<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>

<div class="content_head"><a href="/myStruts/News_action.do?method=list"><bean:message key="news.content.header"/></a> >> <bean:message key="news.view.content.header.topic"/></div>
<div class="content_body">
    <html:form action="/News_action">
        <nested:nest property="newsMessage">
            <nested:hidden property="id"/>
            <div class="news_title">
                <div class="news_title_head"><bean:message key="news.view.news_title"/></div>
                <div class="news_title_body"><pre><nested:write property="title"/></pre></div>
            </div>
            <div class="news_title">
                <div class="news_title_head"><bean:message key="news.view.news_date"/></div>
                <div class="news_title_body">
                    <nested:write property="date" formatKey="format.date"/>
                </div>
            </div>
            <div class="news_title">
                <div class="news_title_head"><bean:message key="news.view.news_brief"/></div>
                <div class="news_title_body"><pre><nested:write property="brief"/></pre></div>
            </div>
            <div class="news_title">
                <div class="news_title_head"><bean:message key="news.view.news_content"/></div>
                <div class="news_title_body"><pre><nested:write property="content"/></pre></div>
            </div>
        </nested:nest>
        <div class="news_title_submit">
            <html:submit property="method" onclick="return askDelete()"><bean:message key="news.view.submit.delete"/></html:submit>
            <html:submit property="method"><bean:message key="news.view.submit.edit"/></html:submit>
        </div>
    </html:form> 
</div>
