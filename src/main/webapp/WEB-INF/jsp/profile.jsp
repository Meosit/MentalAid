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
            <div class="media-body container-fluid">
              <div class="row media-heading">
                <div class="col-xs-10 profile-nickname h3"><span
                    class="glyphicon glyphicon-user"></span> ${requestScope.user.username}</div>
                <div class="col-xs-2 h4"><ms:starRating value="${requestScope.user.averageMark}"
                                                        markCount="${requestScope.user.markCount}"
                                                        showLabel="false"/></div>
              </div>
              <h5 class="profile-label"><span class="glyphicon glyphicon-time"></span> <fmt:message
                  key="label.registered"/>
                <span class="profile-value"><fmt:formatDate type="both" dateStyle="long" timeStyle="short"
                                                            value="${requestScope.user.createdAt}"/></span>
              </h5>
              <h5 class="profile-label"><span class="glyphicon glyphicon-star"></span> <fmt:message key="label.rating"/>
                <span class="profile-value"><fmt:formatNumber value="${requestScope.user.averageMark}"
                                                              minFractionDigits="1" maxFractionDigits="1"/></span>
                (<span class="profile-value">${requestScope.user.markCount}</span> votes)</h5>
              <div class="h5 profile-label"><span
                  class="glyphicon glyphicon-question-sign"></span> <fmt:message key="label.questions"/> <span
                  class="profile-value">${requestScope.questionCount}</span></div>
              <div class="h5 profile-label"><span
                  class="glyphicon glyphicon-exclamation-sign"></span> <fmt:message key="label.answers"/> <span
                  class="profile-value">${requestScope.answerCount}</span></div>
              <c:if test="${not empty requestScope.user.website}">
                <h5 class="profile-label"><span class="glyphicon glyphicon-link"></span> <fmt:message
                    key="label.website"/> <a
                    class="profile-value" href="${requestScope.user.website}">${requestScope.user.website}</a></h5>
              </c:if>
            </div>
          </div>
        </div>
        <c:set var="isAuthorizedUserPage"
               value="${not empty sessionScope.user and sessionScope.user.username eq requestScope.user.username}"/>
        <c:set var="isAuthorizedAdmin" value="${not empty sessionScope.user and sessionScope.user.role eq 1}"/>
        <c:set var="isAdminsPage" value="${requestScope.user.role eq 1}"/>
        <div class="text-right control-div">
          <c:choose>
            <c:when test="${isAuthorizedUserPage}">
              <a href="<c:url value="/controller?cmd=settings"/>" type="button" id="profile-edit-btn"
                 class="btn btn-custom btn-success"><fmt:message key="button.edit"/></a>
            </c:when>
            <c:when test="${isAuthorizedAdmin and not isAdminsPage and not isAuthorizedUserPage}">
              <form id="user-ban-form">
                <input type="hidden" name="username" value="${requestScope.user.username}">
                <c:choose>
                  <c:when test="${sessionScope.user.status eq 0}">
                    <button type="submit" id="user-ban-btn" class="btn btn-custom btn-danger"><fmt:message
                        key="button.ban"/></button>
                  </c:when>
                  <c:otherwise>
                    <button type="submit" id="user-ban-btn" class="btn btn-custom btn-danger"><fmt:message
                        key="button.unban"/></button>
                  </c:otherwise>
                </c:choose>
              </form>
            </c:when>
          </c:choose>
          <div class="text-left" id="result-alert-container"></div>
        </div>
        <jsp:include page="template/question_list.jsp"/>
      </div>
      <jsp:include page="template/footer.jsp"/>
      <script src="<c:url value="/js/profile.js"/>"></script>
    </body>
  </html>
</fmt:bundle>
