<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
        <a class="navbar-brand" href="<c:url value="/index.jsp"/>"><img
            src="<c:url value="/img/logo.png"/>"></a>
      </div>
      <div class="collapse navbar-collapse" id="nav">
        <ul class="nav navbar-nav">
          <li class="${pageContext.request.requestURI eq '/BidBuy/WEB-INF/jsp/home.jsp' ? 'active' : ''}"><a
              href="<c:url value="/index.jsp"/>"><span class="glyphicon glyphicon-home"></span> <fmt:message
              key="button.home"/></a></li>
          <li class="${pageContext.request.requestURI eq '/BidBuy/WEB-INF/jsp/lot_list.jsp' ? 'active' : ''}"><a
              href="#"><span class="glyphicon glyphicon-list-alt"></span> <fmt:message key="button.itemList"/></a></li>
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
                    <li><a href="#"><span class="glyphicon glyphicon-king"></span> <fmt:message
                        key="button.winnings"/></a></li>
                    <li><a href="#"><span class="glyphicon glyphicon-piggy-bank"></span> <fmt:message
                        key="button.myLots"/></a></li>
                    <li class="divider"></li>
                    <li><a href="#"><span class="glyphicon glyphicon-log-out"></span> <fmt:message
                        key="button.logout"/></a></li>
                  </ul>
                </li>
              </c:when>
              <c:otherwise>
                <li><a href="<c:url value="/controller?cmd=get_register_page"/>"><span
                    class="glyphicon glyphicon-plus"></span> <fmt:message key="button.signUp"/></a></li>
                <li><a href="<c:url value="/controller?cmd=get_login_page"/>"><span
                    class="glyphicon glyphicon-log-in"></span>
                  <fmt:message key="button.login"/></a></li>
              </c:otherwise>
            </c:choose>
          </c:if>
          <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">${fn:toUpperCase(sessionScope.locale)} <b
                class="caret"></b></a>
            <ul class="dropdown-menu">
              <li><a href="<c:url value="/controller?cmd=set_locale&locale=en"/>">English</a></li>
              <li><a href="<c:url value="/controller?cmd=set_locale&locale=ru"/>">Русский</a></li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</fmt:bundle>