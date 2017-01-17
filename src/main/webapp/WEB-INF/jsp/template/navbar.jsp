<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ms" uri="http://epam.mksn.by/MentalAid/tag/msTags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="pagecontent" prefix="navbar.">
  <nav class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#nav">
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="<c:url value="/"/>"><img id="logo-image" src="<c:url value="/img/logo.png"/>"></a>
      </div>
      <div class="collapse navbar-collapse" id="nav">
        <ul class="nav navbar-nav">
          <li>
            <a class="logo-label" href="<c:url value="/"/>"><fmt:message key="app.name"/></a>
          </li>
          <c:if test="${not param.isNavbarLess}">
            <c:if test="${not empty sessionScope.user}">
              <li class="${sessionScope.user.status eq 0 ? 'disabled' :''} ${pageContext.request.requestURI eq '/BidBuy/WEB-INF/jsp/new_question.jsp' ? 'active' : ''}">
                <a
                    href="<c:url value="/controller?cmd=new_question"/>"><span class="glyphicon glyphicon-edit"></span>
                  <fmt:message key="button.newQuestion"/></a></li>
            </c:if>
            <c:if test="${sessionScope.user.role eq 1}">
              <li class="${pageContext.request.requestURI eq '/BidBuy/WEB-INF/jsp/user_list.jsp' ? 'active' : ''}"><a
                  href="<c:url value="/controller?cmd=users"/>"><span class="glyphicon glyphicon-list-alt"></span>
                <fmt:message key="button.userList"/></a></li>
            </c:if>
          </c:if>
        </ul>
        <ul class="nav navbar-nav navbar-right">
          <c:if test="${not param.isNavbarLess}">
            <c:choose>
              <c:when test="${not empty sessionScope.user}">
                <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span
                      class="glyphicon glyphicon-user"></span> ${sessionScope.user.username} <b class="caret"></b></a>
                  <ul class="dropdown-menu">
                    <li><a href="#"><span class="glyphicon glyphicon-user"></span> <fmt:message
                        key="button.profile"/></a>
                    </li>
                    <li><a href="#"><span class="glyphicon glyphicon-cog"></span> <fmt:message
                        key="button.settings"/></a>
                    </li>
                    <li class="divider"></li>
                    <li>
                      <a href="<c:url value="/controller?cmd=logout&from=${requestScope.fullUrl}"/>"><span
                          class="glyphicon glyphicon-log-out"></span> <fmt:message key="button.logout"/></a>
                    </li>
                  </ul>
                </li>
              </c:when>
              <c:otherwise>
                <li><a href="<c:url value="/controller?cmd=register&from=${requestScope.fullUrl}"/>"><span
                    class="glyphicon glyphicon-plus"></span> <fmt:message key="button.register"/></a></li>
                <li><a href="<c:url value="/controller?cmd=login&from=${requestScope.fullUrl}"/>"><span
                    class="glyphicon glyphicon-log-in"></span>
                  <fmt:message key="button.login"/></a></li>
              </c:otherwise>
            </c:choose>
          </c:if>
          <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">${fn:toUpperCase(sessionScope.locale)} <b
                class="caret"></b></a>
            <ul class="dropdown-menu">
              <li><a
                  href="<c:url value="/controller?cmd=set_locale&locale=en&from="/>${requestScope.fullUrl}">English</a>
              </li>
              <li><a
                  href="<c:url value="/controller?cmd=set_locale&locale=ru&from="/>${requestScope.fullUrl}">Русский</a>
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</fmt:bundle>