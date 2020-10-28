<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>


<html>
<c:set var="title" value="Attendance"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>


<body>
<div style="margin-left: auto;
    margin-right: auto;
    width: 50em">
    <h3><fmt:message key="attendance.title"/></h3>
    <table class="table">
        <thead>
        <tr>
            <th scope="col"><fmt:message key="attendance.time_col"/></th>
            <th scope="col"><fmt:message key="attendance.people_col"/></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td><fmt:message key="attendance.day_row"/></td>
            <td>${requestScope.amountOfPeopleDay}</td>
        </tr>
        <tr>
            <td><fmt:message key="attendance.week_row"/></td>
            <td>${requestScope.amountOfPeopleWeek}</td>
        </tr>
        <tr>
            <td><fmt:message key="attendance.month_row"/></td>
            <td>${requestScope.amountOfPeopleMonth}</td>
        </tr>
        </tbody>
    </table>
</div>

</body>
</html>
