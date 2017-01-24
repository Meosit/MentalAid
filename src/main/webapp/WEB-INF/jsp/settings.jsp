<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ms" uri="http://epam.mksn.by/MentalAid/tag/msTags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="pagecontent" prefix="settings.">
  <html lang="${sessionScope.locale}">
    <head>
      <title><fmt:message key="title"/></title>
      <jsp:include page="template/links.jsp"/>
      <link rel="stylesheet" href="<c:url value="/css/settings.css"/>">
      <link rel="stylesheet" href="<c:url value="/css/stars.css"/>">
    </head>
    <body>
      <jsp:include page="template/navbar.jsp"/>
      <div class="container">
        <div>
          <h3 class="text-center"><fmt:message key="changePassword.title"/></h3>
          <form class="form-horizontal" id="change-password-form">
            <div class="form-group">
              <label class="control-label col-sm-offset-2 col-sm-2" for="old-password"><fmt:message
                  key="changePassword.current.label"/></label>
              <div class="col-sm-6">
                <input required type="password" class="form-control" id="old-password" name="old_password"
                       title="<fmt:message key="changePassword.current.title"/>"
                       placeholder="<fmt:message key="changePassword.current.placeholder"/>">
              </div>
            </div>
            <div class="form-group">
              <label class="control-label col-sm-offset-2 col-sm-2" for="new-password"><fmt:message
                  key="changePassword.new.label"/></label>
              <div class="col-sm-6">
                <input required type="password" class="form-control" id="new-password" name="new_password"
                       title="<fmt:message key="changePassword.new.title"/>"
                       placeholder="<fmt:message key="changePassword.new.placeholder"/>">
              </div>
            </div>
            <div class="form-group">
              <label class="control-label col-sm-offset-2 col-sm-2" for="new-password-confirm"><fmt:message
                  key="changePassword.new.confirm.label"/></label>
              <div class="col-sm-6">
                <input required type="password" class="form-control" id="new-password-confirm"
                       title="<fmt:message key="changePassword.new.confirm.title"/>"
                       placeholder="<fmt:message key="changePassword.new.confirm.placeholder"/>">
              </div>
            </div>
            <div class="form-group">
              <div class="col-sm-offset-4">
                <button class="btn btn-settings btn-lg btn-primary" type="submit"><fmt:message
                    key="changePassword.button"/></button>
              </div>
            </div>
          </form>
          <div id="change-password-result-div">
          </div>
          <div id="change-password-error-div">
          </div>
        </div>
        <div>
          <h3 class="text-center"><fmt:message key="profileSettings.title"/></h3>
          <form class="form-horizontal" id="profile-update-form">
            <div class="form-group">
              <label class="control-label col-sm-offset-2 col-sm-2" for="image-url"><fmt:message
                  key="profileSettings.imageUrl.label"/></label>
              <div class="col-sm-6">
                <input type="text" class="form-control" id="image-url" name="image_url"
                       value="${sessionScope.user.imageUrl}"
                       title="<fmt:message key="profileSettings.imageUrl.title"/>"
                       placeholder="<fmt:message key="profileSettings.imageUrl.placeholder"/>">
              </div>
            </div>
            <div class="form-group">
              <label class="control-label col-sm-offset-2 col-sm-2" for="website-url"><fmt:message
                  key="profileSettings.website.label"/></label>
              <div class="col-sm-6">
                <input type="text" class="form-control" id="website-url" name="website"
                       value="${sessionScope.user.website}"
                       title="<fmt:message key="profileSettings.website.title"/>"
                       placeholder="<fmt:message key="profileSettings.website.placeholder"/>">
              </div>
            </div>
            <div class="form-group">
              <div class="col-sm-offset-4">
                <button class="btn btn-settings btn-lg btn-primary" type="submit"><fmt:message
                    key="profileSettings.button"/></button>
              </div>
            </div>
            <img class="preview-img col-sm-offset-4"
                 id="avatar-test-image" alt="Avatar"
                 src="${not empty sessionScope.user.imageUrl ? sessionScope.user.imageUrl : 'img/default_avatar.png'}">
          </form>
          <div id="profile-update-result-div">
          </div>
        </div>
      </div>
      <jsp:include page="template/footer.jsp"/>
      <script src="<c:url value="/js/settings.js"/>"></script>
    </body>
  </html>
</fmt:bundle>