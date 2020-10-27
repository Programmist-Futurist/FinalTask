<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<c:set var="title" value="Cinema" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<style>
    .bs-example {
        margin: 20px;
    }

    ul.hr {
        margin: 5% 20%;
        border: 1px solid #000; /* Рамка вокруг текста */
        padding: 3px; /* Поля вокруг текста */
    }

    ul.hr li {
        display: block; /* Отображать как вертикальный элемент */
    }

</style>

<body>
<div style="margin-left: auto;
    margin-right: auto;
    width: 50em">
    <form action="/controller" method="get" style="text-align: center">
        <c:if test="${requestScope.films != null}">
            <input type="hidden" name="command" value="lookSchedule">
            <%@ include file="/WEB-INF/jspf/common/cinema/sort.jspf" %>
            <c:forEach items="${requestScope.films}" var="film">
                <div style="text-align: left; border: #0f0f0f 2px;">
                    <form action="/controller" method="get">
                        <input type="hidden" name="command" value="lookSchedule">
                        <%@ include file="/WEB-INF/jspf/common/cinema/show_film.jspf" %>
                        <input type="hidden" name="filmId" value="${film.id}">
                        <dd class="col-sm-1">
                            <button name="action" value="getFilmSchedule" class="btn btn-primary" style="width: 180px">
                                Look schedule
                            </button>
                        </dd>
                    </form>
                </div>
                <hr style="color: black; height: 4px"/>
            </c:forEach>
        </c:if>

        <c:if test="${requestScope.filmSchedules != null}">
            <form action="/controller" method="get">
                <input type="hidden" name="command" value="lookHall">
                <div style="text-align: left">
                    <%@ include file="/WEB-INF/jspf/common/cinema/show_schedule.jspf" %>
                </div>
            </form>
        </c:if>


        <c:if test="${sessionScope.places != null}">
            <form action="/controller" method="get">
                <input type="hidden" name="command" value="makeOrder">
                <div style="text-align: left">
                    <%@ include file="/WEB-INF/jspf/common/cinema/show_hall.jspf" %>
                    <c:if test="${sessionScope.user != null}">
                        <button name="action" value="makeOrder">Make order</button>
                    </c:if>
                </div>
            </form>
        </c:if>
    </form>
</div>
</body>
</html>
