<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="pagecontent" prefix="login.">
  <html>
    <head>
      <title><fmt:message key="title"/></title>
      <jsp:include page="template/links.jsp"/>
      <link rel="stylesheet" href="<c:url value="/css/login.css"/>">
    </head>
    <body>
      <jsp:include page="template/navbar_empty.jsp"/>
      <div class="container">
        <div class="wrapper">
          <form action="" method="post" name="login_form" class="form-signin">
            <h3 class="form-signin-heading"><fmt:message key="welcomeBack"/></h3>
            <hr class="colorgraph">
            <br>
            <input type="text" class="form-control" name="username" placeholder="Username" required autofocus/>
            <input type="password" class="form-control" name="password" placeholder="Password" required=""/>
            <button class="btn btn-lg btn-primary btn-block" name="submit" value="Login" type="submit"><fmt:message
                key="button.login"/></button>
          </form>
        </div>
      </div>
      <jsp:include page="template/footer.jsp"/>
    </body>
  </html>
</fmt:bundle>
