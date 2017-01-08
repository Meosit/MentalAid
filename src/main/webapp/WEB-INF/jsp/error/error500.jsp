<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="pagecontent" prefix="error.">
  <html>
    <head>
      <title><fmt:message key="title"/></title>
      <jsp:include page="../template/links.jsp"/>
      <link rel="stylesheet" href="<c:url value="/css/error.css"/>">
    </head>
    <body>
      <jsp:include page="../template/navbar.jsp">
        <jsp:param name="isNavbarLess" value="true"/>
      </jsp:include>
      <div class="container">
        <div class="row">
          <div class="col-md-12">
            <div class="error-template">
              <h1><fmt:message key="oops"/></h1>
              <h2><fmt:message key="500.title"/></h2>
              <div class="error-details">
                <fmt:message key="500.message"/>
              </div>
              <div class="error-actions">
                <a href="<c:url value="/index.jsp"/>" class="btn btn-primary btn-lg">
                  <span class="glyphicon glyphicon-home"></span> <fmt:message key="button.takeMeHome"/>
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
      <jsp:include page="../template/footer.jsp"/>
    </body>
  </html>
</fmt:bundle>
