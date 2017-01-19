<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ms" uri="http://epam.mksn.by/MentalAid/tag/msTags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="pagecontent" prefix="profile.">
  <html lang="${sessionScope.locale}">
    <head>
      <title>${requestScope.user.username} | MentalAid</title>
      <jsp:include page="template/links.jsp"/>
      <link rel="stylesheet" href="<c:url value="/css/question_list.css"/>">
      <link rel="stylesheet" href="<c:url value="/css/profile.css"/>">
      <link rel="stylesheet" href="<c:url value="/css/stars.css"/>">
    </head>
    <body>
      <jsp:include page="template/navbar.jsp">
        <jsp:param name="isNavbarLess" value="false"/>
      </jsp:include>
      <div class="container">
        <div class="well">
          <div class="media">
            <span class="pull-left">
            <c:choose>
              <c:when test="${not empty requestScope.user.imageUrl}">
                <img class="media-object profile-img" src="${requestScope.user.imageUrl}" alt="avatar">
              </c:when>
              <c:otherwise>
                <img class="media-object profile-img" src="<c:url value="/img/default_avatar.png"/>" alt="avatar">
              </c:otherwise>
            </c:choose>
          </span>
            <div class="media-body">
              <h3 class="media-heading"><span class="glyphicon glyphicon-user"></span> ${requestScope.user.username}
              </h3>
              <h5>Rating: <ms:starRating value="${requestScope.user.averageMark}"
                                         markCount="${requestScope.user.markCount}"/> (${requestScope.user.markCount}
                votes)</h5>
              <h5>Registered at <span class="glyphicon glyphicon-time"></span> <fmt:formatDate type="both"
                                                                                               dateStyle="long"
                                                                                               timeStyle="short"
                                                                                               value="${requestScope.user.createdAt}"/>
              </h5>
              <h5>Questions: ${requestScope.questionCount}</h5>
              <h5>Answers: ${requestScope.answerCount}</h5>
              <c:if test="${not empty requestScope.user.website}">
                <h5>Website: <a href="${requestScope.user.website}">${requestScope.user.website}</a></h5>
              </c:if>
            </div>
          </div>
        </div>
        <jsp:include page="template/question_list.jsp"/>
      </div>
      <jsp:include page="template/footer.jsp"/>
      <script src="<c:url value="/js/profile.js"/>"></script>
    </body>
  </html>
</fmt:bundle>
