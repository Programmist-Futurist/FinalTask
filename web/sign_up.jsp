<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Registration"/>
<%@ include file="WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<body>

<div style="text-align: center; position: absolute;
top: 40%;
left: 50%;
margin: 0 -50% 0 0;
transform: translate(-50%, -50%)">
    <form action="/controller" method="post">
        <input type="hidden" name="command" value="signUp"/>
        <h1><fmt:message key="sign_up_jsp.page_name.register"/></h1>
        <table align="center">
            <h5 align="left" style="color: red"><fmt:message key="sign_up_jsp.text.necessary"/></h5>

            <tr>
                <a style="color: red">${requestScope.errorMessage}</a>
                <a style="color: limegreen">${requestScope.infoMessage}</a>
            </tr>
            <tr>
                <td><input type="text" name="login"
                           placeholder="<fmt:message key="sign_up_jsp.placeholder.login"/>"></td>
            </tr>
            <tr>
                <td><input type="password" name="password"
                           placeholder="<fmt:message key="sign_up_jsp.placeholder.password"/>"></td>
            </tr>
            <tr>
                <td><input type="text" name="userName"
                           placeholder="<fmt:message key="sign_up_jsp.placeholder.name"/>"></td>
            </tr>

            <tr>
                <td>
                    <h5 align="left"><fmt:message key="sign_up_jsp.text.additional"/></h5>
                    <input type="text" name="userEmail"
                           placeholder="<fmt:message key="sign_up_jsp.placeholder.email"/>">
                </td>
            </tr>
            <tr>
                <td><input type="tel" name="userPhone"
                           placeholder="<fmt:message key="sign_up_jsp.placeholder.phone"/>"></td>
            </tr>
            <form action="/controller" method="post">
                <tr>
                    <td colspan="2" align="left">
                        <br/>
                        <button name="action" value="signUpButton" class="btn-primary"><fmt:message
                                key="sign_up_jsp.btn.sign_up"/></button>
                    </td>
                </tr>
            </form>
            <tr>
                <td colspan="2" align="left">
                    <button name="action" value="loginButton" class="btn-primary"><fmt:message
                            key="sign_up_jsp.btn.login"/></button>
                    <a><fmt:message key="sign_up_jsp.text.some_text"/></a>
                </td>
            </tr>
        </table>
    </form>
</div>
<%@include file="WEB-INF/jspf/footer.jspf" %>
</body>
</html>
