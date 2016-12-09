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
          <li><a href="<c:url value="/"/>"><span class="glyphicon glyphicon-home"></span> <fmt:message
              key="button.home"/></a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
          <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">${fn:toUpperCase(sessionScope.locale)} <b
                class="caret"></b></a>
            <ul class="dropdown-menu">
              <li><a href="#">English</a></li>
              <li><a href="#">Русский</a></li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</fmt:bundle>