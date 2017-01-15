<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ms" uri="http://epam.mksn.by/MentalAid/tag/msTags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="pagecontent" prefix="new_question.">
  <html lang="${sessionScope.locale}">
    <head>
      <title><fmt:message key="title"/></title>
      <jsp:include page="template/links.jsp"/>
      <link rel="stylesheet" href="<c:url value="/css/new_question.css"/>">
      <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
    </head>
    <body>
      <jsp:include page="template/navbar.jsp">
        <jsp:param name="isNavbarLess" value="false"/>
        <jsp:param name="fromUrl" value="${ms:encodeUrl(ms:fullRequestUrl(pageContext.request))}"/>
      </jsp:include>
      <div class="container">
        <div class="well center-block">
          <form id="new-question-form">
            <div class="form-group">
              <h3><label for="question-title-input"><fmt:message key="title.label"/></label></h3>
              <textarea class="form-title form-control" rows="2" name="question_title" id="question-title-input"
                        placeholder="<fmt:message key="title.placeholder"/>"></textarea>
            </div>
            <hr class="question-separator">
            <div class="form-group">
              <h3><label for="question-description-input"><fmt:message key="description.label"/></label></h3>
              <textarea class="form-control" rows="13" name="question_description" id="question-description-input"
                        placeholder="<fmt:message key="description.placeholder"/>"></textarea>
            </div>
            <button type="submit" id="create-question-button" class="btn btn-primary">
              <fmt:message key="button.submit"/>
            </button>
          </form>
        </div>
        <div id="error-div"></div>
      </div>
      <jsp:include page="template/footer.jsp"/>
      <script src="<c:url value="/js/new_question.js"/>"></script>
    </body>
  </html>
</fmt:bundle>