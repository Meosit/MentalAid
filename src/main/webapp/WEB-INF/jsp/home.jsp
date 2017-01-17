<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ms" uri="http://epam.mksn.by/MentalAid/tag/msTags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="pagecontent" prefix="home.">
  <html lang="${sessionScope.locale}">
  <head>
    <title><fmt:message key="title"/></title>
    <jsp:include page="template/links.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/question_list.css"/>">
  </head>
  <body>
  <jsp:include page="template/navbar.jsp">
    <jsp:param name="isNavbarLess" value="false"/>
  </jsp:include>
  <div class="container">
    <jsp:include page="template/question_list.jsp"/>
  </div>
  <jsp:include page="template/footer.jsp"/>
  </body>
  </html>
</fmt:bundle>
