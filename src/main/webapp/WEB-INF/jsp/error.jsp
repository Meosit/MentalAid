<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="pagecontent" prefix="error.">
  <html>
    <head>
      <title><fmt:message key="title"/></title>
      <jsp:include page="template/links.jsp"/>
      <link rel="stylesheet" href="<c:url value="/css/error.css"/>">
    </head>
    <body>
      <jsp:include page="template/navbar_empty.jsp"/>
      <div class="container">
        <div class="row">
          <div class="col-md-12">
            <div class="error-template">
              <h1><fmt:message key="oops"/></h1>
              <h2><fmt:message key="${requestScope.errorCode}"/></h2>
              <div class="error-details">
                <fmt:message key="${requestScope.errorMessage}"/>
              </div>
              <div class="error-actions">
                <a href="<c:url value="/index.jsp"/>" class="btn btn-primary btn-lg">
                  <span class="glyphicon glyphicon-home"></span> <fmt:message key="button.takeMeHome"/>
                </a>
                <a href="#" class="btn btn-default btn-lg">
                  <span class="glyphicon glyphicon-envelope"></span> <fmt:message key="button.contactSupport"/>
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
      <jsp:include page="template/footer.jsp"/>
    </body>
  </html>
</fmt:bundle>
