<%@ attribute name="free" required="false" %>
<%@ attribute name="num" required="true" %>
<input type="hidden" name="command" value="bookPlace">
<c:if test="${free.equals('true')}">
    <input type="hidden" name="placeId" value="${num}">
    <button style="color: #2D5BA3; width:40px; height: 40px;" name="action" value="bookPlace">${num}</button>
</c:if>
<c:if test="${free.equals('false')}">
    <button style="color: darkorange; width:40px; height: 40px;" name="action" value="">${num}</button>
</c:if>

