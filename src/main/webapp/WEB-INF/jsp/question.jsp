<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ms" uri="http://epam.mksn.by/MentalAid/tag/msTags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="pagecontent" prefix="question.">
<html lang="${sessionScope.locale}">
<head>
  <title><c:out value="${requestScope.question.title}"/> | Mental Aid</title>
  <jsp:include page="template/links.jsp"/>
  <link rel="stylesheet" href="<c:url value="/css/question.css"/>">
  <link rel="stylesheet" href="<c:url value="/css/stars.css"/>">
</head>
<body>
<jsp:include page="template/navbar.jsp">
  <jsp:param name="isNavbarLess" value="false"/>
</jsp:include>
<div class="container">
  <div class="question">
    <h3><c:out value="${requestScope.question.title}"/></h3>
    <hr class="question-separator">
    <p class="description">
      <c:set var="newLineChar" value="
"/>
      <c:out value="${fn:replace(requestScope.question.description, newLineChar, '<br>')}"/>
    </p>
    <h5 class="text-right">
      Asked by <a href="#">${requestScope.question.creatorUsername}</a> at
      <span class="glyphicon glyphicon-time"></span> <fmt:formatDate
        value="${requestScope.question.createdAt}" pattern="dd MMMM, yyyy HH:mm"/>
    </h5>
    <c:if test="${requestScope.question.createdAt ne requestScope.question.modifiedAt}">
      <h6 class="edited-caption"><span class="glyphicon glyphicon-edit"></span> Edited at
        <fmt:formatDate
            value="${requestScope.question.modifiedAt}" pattern="dd MMMM, yyyy HH:mm"/></h6>
    </c:if>
  </div>

  <div class="answers">
    <div class="answers-header">
      <h3>${requestScope.question.answerCount} <c:choose>
        <c:when test="${requestScope.question.answerCount eq 1}">
          Answer
        </c:when>
        <c:otherwise>
          Answers
        </c:otherwise>
      </c:choose></h3>
      <hr class="question-separator">
    </div>
    <c:forEach items="${requestScope.answers}" var="answer">
      <div class="answer">
        <div class="media">
          <div class="media-body">
            <p>
              <c:out value="${fn:replace(answer.text, newLineChar, '<br>')}"/>
            </p>
            <div class="text-right">
              <ms:starRating value="${answer.averageMark}"/>
              <h5 class="media-heading">
                | by <a href="#">${answer.creatorUsername}</a> at
                <fmt:formatDate
                    value="${answer.createdAt}" pattern="dd MMMM, yyyy HH:mm"/>
              </h5>
              <c:if test="${requestScope.question.createdAt ne requestScope.question.modifiedAt}">
                <h6 class="edited-caption"><span class="glyphicon glyphicon-edit"></span> Edited at
                  <fmt:formatDate
                      value="${answer.modifiedAt}" pattern="dd MMMM, yyyy HH:mm"/></h6>
              </c:if>
            </div>
          </div>
          <hr>
        </div>
      </div>
    </c:forEach>
  </div>
  <div class="well">
    <h4>Leave an answer:</h4>
    <form role="form">
      <div class="form-group">
        <textarea class="form-control" rows="3"></textarea>
      </div>
      <button type="submit" class="btn btn-primary">Submit</button>
    </form>
  </div>
</div>
<jsp:include page="template/footer.jsp"/>
</body>
</html>
</fmt:bundle>