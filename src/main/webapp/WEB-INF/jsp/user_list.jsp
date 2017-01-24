<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ms" uri="http://epam.mksn.by/MentalAid/tag/msTags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="pagecontent" prefix="user_list.">
  <html lang="${sessionScope.locale}">
    <head>
      <title><fmt:message key="title"/></title>
      <jsp:include page="template/links.jsp"/>
      <link rel="stylesheet" href="<c:url value="/css/user_list.css"/>">
    </head>
    <body>
      <jsp:include page="template/navbar.jsp"/>
      <div class="container">
        <jsp:include page="template/search_bar.jsp"/>
        <c:forEach var="user" items="${requestScope.users}">
          <div class="well">
            <div class="media">
              <span class="pull-left">
                <a href="<c:url value="/controller?cmd=profile&username=${user.username}"/>">
                  <c:choose>
                    <c:when test="${not empty user.imageUrl}">
                      <img id="profile-image" class="media-object profile-img" alt="avatar" src="${user.imageUrl}">
                    </c:when>
                    <c:otherwise>
                      <img id="profile-image" class="media-object profile-img" alt="avatar"
                           src="<c:url value="/img/default_avatar.png"/>">
                    </c:otherwise>
                  </c:choose>
                </a>
              </span>
              <div class="media-body container-fluid">
                <div class="media-heading">
                  <div class="profile-nickname h3">
                    <a href="<c:url value="/controller?cmd=profile&username=${user.username}"/>"><span
                        class="glyphicon glyphicon-user"></span> ${user.username}</a>
                    <c:if test="${user.role eq 1}">
                      <span class="admin-label h4">[<fmt:message key="label.admin"/>]</span>
                    </c:if>
                  </div>
                </div>
                <div class="h6 profile-label"><span class="glyphicon glyphicon-time"></span>
                  <fmt:message key="label.registered"/>
                  <span class="profile-value"><fmt:formatDate type="both" dateStyle="long" timeStyle="short"
                                                              value="${user.createdAt}"/></span>
                </div>
                <div class="h6 profile-label"><span class="glyphicon glyphicon-envelope"></span>
                  <fmt:message key="label.email"/>
                  <span class="profile-value"><a href="mailto:${user.email}">${user.email}</a></span>
                </div>
                <div class="h6 profile-label"><span class="glyphicon glyphicon-flag"></span>
                  <fmt:message key="label.status"/>
                  <c:choose>
                    <c:when test="${user.status eq -1}">
                      <span class="label label-danger"><fmt:message key="label.status.deleted"/></span>
                    </c:when>
                    <c:when test="${user.status eq 0}">
                      <span class="label label-warning"><fmt:message key="label.status.banned"/></span>
                    </c:when>
                    <c:when test="${user.status eq 1}">
                      <span class="label label-success"><fmt:message key="label.status.active"/></span>
                    </c:when>
                  </c:choose>
                </div>
              </div>
            </div>
          </div>
        </c:forEach>
        <div class="row text-center">
          <ms:bootstrapPagination baseUrl="${ms:fullRequestUrl(pageContext.request)}"
                                  currentPage="${requestScope.currentPage}" pageCount="${requestScope.pageCount}"/>
        </div>
      </div>
      <jsp:include page="template/footer.jsp"/>
      <script src="<c:url value="/js/profile.js"/>"></script>
    </body>
  </html>
</fmt:bundle>