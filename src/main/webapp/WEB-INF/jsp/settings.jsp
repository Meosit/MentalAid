<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ms" uri="http://epam.mksn.by/MentalAid/tag/msTags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="pagecontent" prefix="settings.">
  <html lang="${sessionScope.locale}">
    <head>
      <title>Settings | MentalAid</title>
      <jsp:include page="template/links.jsp"/>
      <link rel="stylesheet" href="<c:url value="/css/settings.css"/>">
      <link rel="stylesheet" href="<c:url value="/css/stars.css"/>">
    </head>
    <body>
      <jsp:include page="template/navbar.jsp">
        <jsp:param name="isNavbarLess" value="false"/>
      </jsp:include>
      <div class="container">
        <h3 class="text-center">Change password</h3>
        <div>
          <form class="form-horizontal" id="change-password-form">
            <div class="form-group">
              <label class="control-label col-sm-offset-2 col-sm-2 required-mark" for="old-password">Old
                password</label>
              <div class="col-sm-6">
                <input required type="password" class="form-control" id="old-password" name="old_password"
                       title="Old password"
                       placeholder="Old password">
              </div>
            </div>
            <div class="form-group">
              <label class="control-label col-sm-offset-2 col-sm-2 required-mark" for="new-password">New
                password</label>
              <div class="col-sm-6">
                <input required type="password" class="form-control" id="new-password" name="new_password"
                       title="New password"
                       placeholder="New password">
              </div>
            </div>
            <div class="form-group">
              <label class="control-label col-sm-offset-2 col-sm-2 required-mark" for="new-password-confirm">New
                password again</label>
              <div class="col-sm-6">
                <input required type="password" class="form-control" id="new-password-confirm"
                       title="New password again"
                       placeholder="New password again">
              </div>
            </div>
            <div class="form-group">
              <div class="text-center">
                <button class="btn-register btn btn-lg btn-primary" type="submit">Change password</button>
              </div>
            </div>
          </form>
          <div id="success-div">
          </div>
          <div id="error-div">
            <div id="error-alert" class="alert alert-danger hidden">
              <strong id="error-title"></strong> <span id="error-message"></span>
            </div>
          </div>
        </div>
      </div>
      <jsp:include page="template/footer.jsp"/>
      <script src="<c:url value="/js/settings.js"/>"></script>
    </body>
  </html>
</fmt:bundle>