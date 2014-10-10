<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ page isELIgnored="false" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div class="content_head"><a href="/myStruts/News_action.do?method=list"><bean:message key="news.content.header"/></a> >> <bean:message key="news.list.content.header.topic"/></div>
<div class="content_body">
    <html:form action="/News_action" method="post">
        <logic:iterate name="newsForm" property="newsList" id="news">
            <div class="news">
                <div class="news_time"><bean:write name="news" property="date" formatKey="format.date" /></div>
                <div class="news_head"><pre><bean:write name="news" property="title" /></pre></div>                
                <div class="news_content"><pre><bean:write name="news" property="brief" /></pre></div>
                <div class="news_menu">
                    <a href="/myStruts/News_action.do?method=view&newsMessage.id=${news.id}"><bean:message key="news.list.content.menu.view"/></a>
                    <a href="/myStruts/News_action.do?method=edit&newsMessage.id=${news.id}"><bean:message key="news.list.content.menu.edit"/></a>
                    <input type="checkbox" name="newsMessage.id" value="${news.id}"/>
                </div>
            </div>
        </logic:iterate>
        <div class="news_title_submit">
            <html:submit property="method" onclick="return askAllDelete()">
                <bean:message key="news.list.submit.delete"/>
            </html:submit>
        </div>
    </html:form>
</div>