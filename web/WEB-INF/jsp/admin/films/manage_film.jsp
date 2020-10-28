<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>


<html>
<c:set var="title" value="Manage films"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>


<body>
<form action="/controller" method="post">
    <div>
        <tr align="center">
            <a style="color: red">${requestScope.errorMessage}</a>
            <a style="color: limegreen">${requestScope.infoMessage}</a>
        </tr>
    </div>
    <c:if test="${requestScope.filmAction == 1}">
        <%@ include file="/WEB-INF/jspf/admin/films/film_create.jspf" %>
    </c:if>
    <c:if test="${requestScope.filmAction == 2}">
        <%@ include file="/WEB-INF/jspf/admin/films/film_edit.jspf" %>
    </c:if>
    <c:if test="${requestScope.filmAction == 3}">
        <%@ include file="/WEB-INF/jspf/admin/films/film_delete.jspf" %>
    </c:if>
    <%--    <%@ include file="/WEB-INF/jspf/admin/films/add/script/film_form_script.jspf" %>--%>
</body>
</html>

