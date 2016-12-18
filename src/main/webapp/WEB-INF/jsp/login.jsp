<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="pagecontent" prefix="login.">
  <html lang="${sessionScope.locale}">
    <head>
      <title><fmt:message key="title"/></title>
      <jsp:include page="template/links.jsp"/>
      <link rel="stylesheet" href="<c:url value="/css/login.css"/>">
      <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
    </head>
    <body>
      <jsp:include page="template/navbar.jsp">
        <jsp:param name="isNavbarLess" value="true"/>
      </jsp:include>
      <div class="container">
        <div class="wrapper">
          <form id="login-form" class="form-signin">
            <h3 class="form-signin-heading"><fmt:message key="welcomeBack"/></h3>
            <hr class="colorgraph">
            <br>
            <input type="text" class="form-control" id="username" name="username" placeholder="Username" required
                   autofocus/>
            <input type="password" class="form-control" id="password" name="password" placeholder="Password"
                   required=""/>
            <button class="btn btn-lg btn-primary btn-block" id="login-button" name="submit" value="Login"
                    type="submit">
              <fmt:message
                key="button.login"/></button>
          </form>
          <div id="error-alert" class="alert alert-danger hidden">
            <strong id="error-title"></strong> <span id="error-message"></span>
          </div>
        </div>
      </div>
      <jsp:include page="template/footer.jsp"/>
    </body>
  </html>
  <script src="<c:url value="/js/login.js"/>"></script>
</fmt:bundle>
