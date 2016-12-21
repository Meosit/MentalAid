<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

  <head>
    <title></title>
</head>
<body>
  <c:choose>
    <c:when test="${(not empty sessionScope.user) and (sessionScope.user.role eq 1)}">
      <jsp:forward page="/WEB-INF/jsp/admin_home.jsp"/>
    </c:when>
    <c:otherwise>
      <jsp:forward page="/WEB-INF/jsp/home.jsp"/>
    </c:otherwise>
  </c:choose>
</body>
</html>