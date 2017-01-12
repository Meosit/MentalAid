<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ms" uri="http://epam.mksn.by/MentalAid/tag/msTags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="pagecontent" prefix="question.">
  <c:set var="isAdmin" value="${(not empty sessionScope.user) and (sessionScope.user.role eq 1)}"/>
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
        <jsp:param name="fromUrl" value="${ms:encodeUrl(ms:fullRequestUrl(pageContext.request))}"/>
      </jsp:include>
      <div class="container">
        <c:set var="isQuestionOwner"
               value="${(not empty sessionScope.user) and (requestScope.question.creatorId eq sessionScope.user.id)}"/>
        <div>
          <div id="question-div">
            <h3 id="question-title"><c:out value="${requestScope.question.title}"/></h3>
            <hr class="question-separator">
            <p id="question-description" class="description"><c:out value="${requestScope.question.description}"/></p>
          </div>
          <c:if test="${isAdmin or isQuestionOwner}">
            <div class="hidden" id="question-edit-div">
              <form id="question-edit-form">
                <div class="form-group">
                  <label for="question-title-edit"></label>
                  <textarea class="form-title form-control" rows="1" name="question_title"
                            id="question-title-edit"></textarea>
                </div>
                <hr class="question-separator">
                <div class="form-group">
                  <label for="question-description-edit"></label>
                  <textarea class="form-control" rows="10" name="question_description"
                            id="question-description-edit"></textarea>
                </div>
                <input type="hidden" name="question_id" value="${requestScope.question.id}">
              </form>
            </div>
          </c:if>
          <h5 class="text-right">
            Asked by <a class="${isQuestionOwner ? 'owner' : ''}" href="#">${requestScope.question.creatorUsername}</a>
            at <span class="glyphicon glyphicon-time"></span> <fmt:formatDate
              value="${requestScope.question.createdAt}" type="both" dateStyle="long" timeStyle="short"/>
          </h5>
          <h6 id="question-edit-date-container"
              class="edited-caption ${(requestScope.question.createdAt ne requestScope.question.modifiedAt) ? '' : 'hidden'}">
            <span class="glyphicon glyphicon-edit"></span> Edited at <span id="question-edit-date"><fmt:formatDate
              value="${requestScope.question.modifiedAt}" type="both" dateStyle="long" timeStyle="short"/></span></h6>
          <c:if test="${isAdmin or isQuestionOwner}">
            <div class="text-right">
              <a type="button" id="question-edit-btn" class="btn btn-custom btn-warn">Edit</a>
              <a type="button" id="question-delete-btn" class="btn btn-custom btn-dang">Delete</a>
              <a type="button" id="question-apply-btn" class="hidden btn btn-custom btn-conf">Apply</a>
              <a type="button" id="question-cancel-btn" class="hidden btn btn-custom btn-warn">Cancel</a>
            </div>
          </c:if>
          <div id="question-result-alert-container">
          </div>
        </div>

        <div class="answers">
          <div class="answers-header">
            <a class="anchor" id="answers"></a>
            <h3><span id="answer-count">${requestScope.question.answerCount}</span>
              <c:choose>
                <c:when test="${requestScope.question.answerCount eq 1}">
                  Answer
                </c:when>
                <c:otherwise>
                  Answers
                </c:otherwise>
              </c:choose>
            </h3>
            <hr class="question-separator">
          </div>
          <c:forEach items="${requestScope.answers}" var="answer">
            <c:set var="isAnswerOwner"
                   value="${(not empty sessionScope.user) and (answer.creatorId eq sessionScope.user.id)}"/>
            <c:if test="${isAnswerOwner}">
              <c:set var="isAnswerExists" value="true"/>
            </c:if>
            <div id="answer-div-${answer.id}">
              <div class="media">
                <div class="media-body ${isAnswerOwner ? 'owner-border' : ''}">
                  <p id="answer-text-${answer.id}" class="answer-text"><c:out value="${answer.text}"/></p>
                  <c:if test="${isAdmin or isAnswerOwner}">
                    <div class="hidden" id="answer-text-edit-container-${answer.id}">
                      <form id="answer-text-edit-form-${answer.id}">
                        <div class="form-group">
                          <label for="answer-text-edit-${answer.id}"></label>
                          <textarea class="answer-text-edit form-control" rows="4" name="answer_text"
                                    id="answer-text-edit-${answer.id}"></textarea>
                        </div>
                        <input type="hidden" name="answer_id" value="${answer.id}">
                      </form>
                    </div>
                  </c:if>
                  <div>
                    <div class="col-md-6 text-left">
                      <ms:starRating value="${answer.averageMark}"/>
                    </div>
                    <h5 class="media-heading col-md-6 text-right">by <a class="${isAnswerOwner ? 'owner' : ''}"
                                                                        href="#">${answer.creatorUsername}</a> at <span
                        class="glyphicon glyphicon-time"></span><span
                        class="create-date"><fmt:formatDate value="${answer.createdAt}"
                                                            type="both" dateStyle="long" timeStyle="short"/></span></h5>
                    <h6 id="answer-edit-date-container-${answer.id}"
                        class="edited-caption ${(answer.createdAt ne answer.modifiedAt) ? '' : 'hidden'}"><span
                        class="glyphicon glyphicon-edit"></span> Edited at <span
                        id="answer-edit-date-${answer.id}"><fmt:formatDate
                        value="${answer.modifiedAt}" type="both" dateStyle="long" timeStyle="short"/></span></h6>
                  </div>
                  <c:if test="${isAdmin or isAnswerOwner}">
                    <div class="col-xs-12 text-right">
                      <a type="button" id="answer-edit-${answer.id}" class="answer-edit-btn btn btn-custom btn-warn">Edit</a>
                      <a type="button" id="answer-delete-${answer.id}"
                         class="answer-delete-btn btn btn-custom btn-dang">Delete</a>
                      <a type="button" id="answer-apply-${answer.id}"
                         class="answer-apply-btn hidden btn btn-custom btn-conf">Apply</a>
                      <a type="button" id="answer-cancel-${answer.id}"
                         class="answer-cancel-btn hidden btn btn-custom btn-warn">Cancel</a>
                    </div>
                  </c:if>
                </div>
                <div id="answer-result-alert-container-${answer.id}"></div>
                <hr>
              </div>
            </div>
          </c:forEach>
        </div>
        <div class="well">
          <c:choose>
            <c:when test="${(not empty sessionScope.user) and (not isQuestionOwner) and (empty isAnswerExists)}">
              <form id="new-answer-form">
                <div class="form-group">
                  <h4><label for="new-answer">Leave an answer:</label></h4>
                  <textarea id="new-answer" class="form-control" rows="4"></textarea>
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
              </form>
            </c:when>
            <c:when test="${isQuestionOwner}">
              You cannot answer to your own question, you can edit your question instead.
            </c:when>
            <c:when test="${isAnswerExists}">
              You cannot post two answers, edit existed answer instead.
            </c:when>
            <c:otherwise>
              To leave an answer, <a
                href="<c:url value="/controller?cmd=login&from=${ms:encodeUrl(ms:fullRequestUrl(pageContext.request))}"/>">Login</a> or
              <a
                  href="<c:url value="/controller?cmd=register&from=${ms:encodeUrl(ms:fullRequestUrl(pageContext.request))}"/>">Register</a>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
      <jsp:include page="template/footer.jsp"/>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/bootbox.js/4.4.0/bootbox.min.js"></script>
      <script src="<c:url value="/js/question.js"/>"></script>
    </body>
  </html>
</fmt:bundle>