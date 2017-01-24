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
      <jsp:include page="template/navbar.jsp"/>
      <div class="container">
        <div>
          <h3 class="text-center">Change password</h3>
          <form class="form-horizontal" id="change-password-form">
            <div class="form-group">
              <label class="control-label col-sm-offset-2 col-sm-2" for="old-password">Old
                password</label>
              <div class="col-sm-6">
                <input required type="password" class="form-control" id="old-password" name="old_password"
                       title="Old password"
                       placeholder="Old password">
              </div>
            </div>
            <div class="form-group">
              <label class="control-label col-sm-offset-2 col-sm-2" for="new-password">New
                password</label>
              <div class="col-sm-6">
                <input required type="password" class="form-control" id="new-password" name="new_password"
                       title="New password"
                       placeholder="New password">
              </div>
            </div>
            <div class="form-group">
              <label class="control-label col-sm-offset-2 col-sm-2" for="new-password-confirm">New
                password again</label>
              <div class="col-sm-6">
                <input required type="password" class="form-control" id="new-password-confirm"
                       title="New password again"
                       placeholder="New password again">
              </div>
            </div>
            <div class="form-group">
              <div class="col-sm-offset-4">
                <button class="btn btn-settings btn-lg btn-primary" type="submit">Change password</button>
              </div>
            </div>
          </form>
          <div id="change-password-result-div">
          </div>
          <div id="change-password-error-div">
          </div>
        </div>
        <div>
          <h3 class="text-center">Profile settings</h3>
          <form class="form-horizontal" id="profile-update-form">
            <div class="form-group">
              <label class="control-label col-sm-offset-2 col-sm-2" for="image-url">Avatar url</label>
              <div class="col-sm-6">
                <input type="text" class="form-control" id="image-url" name="image_url"
                       value="${sessionScope.user.imageUrl}"
                       title="Avatar image link"
                       placeholder="Avatar image link">
              </div>
            </div>
            <div class="form-group">
              <label class="control-label col-sm-offset-2 col-sm-2" for="website-url">Website</label>
              <div class="col-sm-6">
                <input type="text" class="form-control" id="website-url" name="website"
                       value="${sessionScope.user.website}"
                       title="Website"
                       placeholder="Website">
              </div>
            </div>
            <div class="form-group">
              <div class="col-sm-offset-4">
                <button class="btn btn-settings btn-lg btn-primary" type="submit">Save changes</button>
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