<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Profile" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<%-- HEADER --%>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%-- HEADER --%>

<form action="/controller" method="post">
    <input type="hidden" name="command" value="profile"/>
    <h6 class="display-4" style="text-align: center; color: blue"><fmt:message key="profile.profile_title_text"/></h6>

    <div class="content" style="border: #0f0f0f 1px;
text-align: center;
margin-right: 10px;
padding: 2px; ">
        <table>
            <tr align="center">
                <c:if test="${not empty sessionScope.user}">
                    <c:set var="user" value="${sessionScope.user}"/>
                    <ul class="bs-example border">
                        <h6>${user.name}</h6>

                        <c:if test="${user.phone != null && not empty user.phone}">
                            <h6>${user.phone}</h6>
                        </c:if>
                        <c:if test="${user.email != null && not empty user.email}">
                            <h6>${user.email}</h6>
                        </c:if>
                    </ul>
                </c:if>
            </tr>
            <br/><br/>
        </table>
    </div>
</form>
<hr/>
<%@ include file="/WEB-INF/jspf/user/tickets.jspf" %>
<br/><br/><br/>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
