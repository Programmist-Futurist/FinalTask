<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Login"/>
<%@ include file="WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<body>

<div style="text-align: center; position: absolute;
top: 40%;
left: 50%;
margin: 0 -50% 0 0;
transform: translate(-50%, -50%)">
    <form action="/controller" method="post">
        <input type="hidden" name="command" value="login"/>
        <h1><fmt:message key="login_jsp.page_name.login"/></h1>
        <table align="center">
            <tr>
                <td><input type="text" name="login"
                           placeholder="<fmt:message key="login_jsp.placeholder.login"/>"></td>
            </tr>
            <tr>
                <td><input type="password" name="password"
                           placeholder="<fmt:message key="login_jsp.placeholder.password"/>"></td>
            </tr>
            <tr>
                <td colspan="2" align="right">
                    <button name="action" value="loginButton" class="btn-primary"><fmt:message
                            key="login_jsp.btn.login"/></button>
                </td>
            </tr>
            <tr>
                <td><fmt:message key="login_jsp.text.do_you_have_an_account"/></td>
                <td colspan="2" align="right">
                    <button name="action" value="signUpButton" class="btn-primary"><fmt:message
                            key="login_jsp.btn.sign_up"/></button>
                </td>
            </tr>
            <tr align="center">
                <a style="color: red">${requestScope.errorMessage}</a>
            </tr>
        </table>
    </form>
</div>
<%@include file="WEB-INF/jspf/footer.jspf" %>
</body>
</html>