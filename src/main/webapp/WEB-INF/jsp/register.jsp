<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="pagecontent" prefix="register.">
  <html>
    <head>
      <title><fmt:message key="title"/></title>
      <jsp:include page="template/links.jsp"/>
      <link rel="stylesheet" href="<c:url value="/css/register.css"/>">
    </head>
    <body>
      <jsp:include page="template/navbar_empty.jsp"/>
      <div class="container">
        <div class="wrapper">
          <fieldset>
            <h2 class="text-center"><fmt:message key="header"/></h2>
            <form class="form-horizontal" id="registration-form">
              <div class="form-group">
                <label class="control-label col-sm-2 required-mark" for="username"><fmt:message
                    key="username.label"/></label>
                <div class="col-sm-10">
                  <input required type="text" class="bg-danger form-control" id="username"
                         title="<fmt:message key="username.hint"/>"
                         placeholder="User1233">
                </div>
              </div>
              <div class="form-group">
                <label class="control-label col-sm-2 required-mark" for="email"><fmt:message key="email.label"/></label>
                <div class="col-sm-10">
                  <input type="email" class="form-control" name="email" id="email" placeholder="example@example.com">
                </div>
              </div>
              <div class="form-group">
                <label class="control-label col-sm-2 required-mark" for="password"><fmt:message
                    key="password.label"/></label>
                <div class="col-sm-10">
                  <input required type="password" class="form-control" id="password"
                         title="<fmt:message key="password.hint"/>"
                         placeholder="<fmt:message key="password.placeholder"/>">
                </div>
              </div>
              <div class="form-group">
                <label class="control-label col-sm-2 required-mark" for="password-confirm"><fmt:message
                    key="password.confirm.label"/></label>
                <div class="col-sm-10">
                  <input required type="password" class="form-control" id="password-confirm"
                         placeholder="<fmt:message key="password.confirm.placeholder"/>">
                </div>
              </div>
              <div class="control-group">
                <!-- Button -->
                <div class="col-sm-offset-2 col-sm-10">
                  <button class="btn btn-lg btn-primary btn-block" name="submit" type="submit"><fmt:message
                      key="button.register"/></button>
                </div>
              </div>
            </form>
          </fieldset>
        </div>
      </div>
      <jsp:include page="template/footer.jsp"/>
      <script src="<c:url value="/js/register.js"/>"></script>
    </body>
  </html>
</fmt:bundle>
