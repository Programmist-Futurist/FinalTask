<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Settings" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>


<body>
<table id="main-container">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <div class="input-group-text">
        <form action="/controller" method="post" style="margin-left: 20%">
            <%@ include file="/WEB-INF/jspf/settings/edit_profile.jspf" %>
            <input type="hidden" name="command" value="editProfile"/>
        </form>
        <div>
            <a style="color: red">${requestScope.errorMessage}</a>
            <a style="color: limegreen">${requestScope.infoMessage}</a>
        </div>
    </div>
</table>
<br/><br/><br/>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>