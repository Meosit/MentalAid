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
      <title><c:out value="${requestScope.question.title}"/> | MentalAid</title>
      <jsp:include page="template/links.jsp"/>
      <link rel="stylesheet" href="<c:url value="/css/question.css"/>">
      <link rel="stylesheet" href="<c:url value="/css/stars.css"/>">
    </head>
    <body>
      <jsp:include page="template/navbar.jsp"/>
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
                  <textarea class="form-title form-control" rows="1" name="question_title" id="question-title-edit"
                            placeholder="<fmt:message key="question.title.placeholder"/>"></textarea>
                </div>
                <hr class="question-separator">
                <div class="form-group">
                  <label for="question-description-edit"></label>
                  <textarea class="form-control" rows="10" name="question_description" id="question-description-edit"
                            placeholder="<fmt:message key="question.description.placehloder"/>"></textarea>
                </div>
                <input type="hidden" id="question-id" name="question_id" value="${requestScope.question.id}">
              </form>
            </div>
          </c:if>
          <h5 class="text-right">
            <fmt:message key="question.by"/> <a class="${isQuestionOwner ? 'owner' : ''}"
                                                href="<c:url value="/controller?cmd=profile&username=${requestScope.question.creatorUsername}"/>">${requestScope.question.creatorUsername}</a>
            <fmt:message key="at"/> <span class="glyphicon glyphicon-time"></span> <fmt:formatDate
              value="${requestScope.question.createdAt}" type="both" dateStyle="long" timeStyle="short"/>
          </h5>
          <h6 id="question-edit-date-container"
              class="edited-caption ${(requestScope.question.createdAt ne requestScope.question.modifiedAt) ? '' : 'hidden'}">
            <span class="glyphicon glyphicon-edit"></span> <fmt:message key="editedAt"/> <span
              id="question-edit-date"><fmt:formatDate
              value="${requestScope.question.modifiedAt}" type="both" dateStyle="long" timeStyle="short"/></span></h6>
          <c:if test="${isAdmin or isQuestionOwner}">
            <div class="text-right">
              <a type="button" id="question-edit-btn" class="btn btn-custom btn-warn"><fmt:message
                  key="button.edit"/></a>
              <a type="button" id="question-delete-btn" class="btn btn-custom btn-dang"><fmt:message
                  key="button.delete"/></a>
              <a type="button" id="question-apply-btn" class="hidden btn btn-custom btn-conf"><fmt:message
                  key="button.apply"/></a>
              <a type="button" id="question-cancel-btn" class="hidden btn btn-custom btn-warn"><fmt:message
                  key="button.cancel"/></a>
            </div>
          </c:if>
          <div id="question-result-alert-container">
          </div>
        </div>
        <div class="answers">
          <div class="answers-header">
            <a class="anchor" id="answers"></a>
            <h3>
              <span id="answer-count-label">${requestScope.question.answerCount}
                <c:choose>
                  <c:when test="${requestScope.question.answerCount eq 1}">
                    <fmt:message key="answer.count.single"/>
                  </c:when>
                  <c:when test="${(requestScope.question.answerCount % 10) eq 1}">
                    <c:choose>
                      <c:when test="${requestScope.locale eq 'ru'}}">
                        <fmt:message key="answer.count.single"/>
                      </c:when>
                      <c:otherwise>
                        <fmt:message key="answer.count.multiple"/>
                      </c:otherwise>
                    </c:choose>
                  </c:when>
                  <c:when
                      test="${((requestScope.question.answerCount % 10) lt 5) and ((requestScope.question.answerCount % 10) ne 0)}">
                    <fmt:message key="answer.count.alter"/>
                  </c:when>
                  <c:otherwise>
                    <fmt:message key="answer.count.multiple"/>
                  </c:otherwise>
                </c:choose>
              </span>
            </h3>
            <hr class="question-separator">
          </div>
          <c:forEach items="${requestScope.answers}" var="answer">
            <c:set var="isAnswerOwner"
                   value="${(not empty sessionScope.user) and (answer.creatorId eq sessionScope.user.id)}"/>
            <c:if test="${isAnswerOwner}">
              <c:set var="isAnswerExists" value="true"/>
            </c:if>
            <div class="answer" id="answer-${answer.id}">
              <div class="media">
                <div class="media-body ${isAnswerOwner ? 'owner-border' : ''}">
                  <p class="answer-text"><c:out value="${answer.text}"/></p>
                  <c:if test="${isAdmin or isAnswerOwner}">
                    <div class="answer-edit-container hidden">
                      <form class="answer-edit-form">
                        <div class="form-group">
                          <label>
                            <textarea class="answer-text-input form-control" rows="4" name="answer_text"
                                      placeholder="<fmt:message key="answer.text.placeholder"/>"></textarea>
                          </label>
                        </div>
                        <input type="hidden" class="answer-id-input" name="answer_id" value="${answer.id}">
                      </form>
                    </div>
                  </c:if>
                  <div>
                    <div class="col-md-6 text-left">
                      <ms:starRating value="${answer.averageMark}" markCount="${answer.markCount}"
                                     canVote="${(empty sessionScope.user or isAnswerOwner) ? false : true}"/>
                    </div>
                    <h5 class="media-heading col-md-6 text-right">
                      <fmt:message key="answer.by"/> <a class="${isAnswerOwner ? 'owner' : ''}"
                                                        href="<c:url value="/controller?cmd=profile&username=${answer.creatorUsername}"/>">${answer.creatorUsername}</a>
                      <fmt:message key="at"/> <span class="glyphicon glyphicon-time"></span>
                      <span class="answer-created-date create-date">
                        <fmt:formatDate value="${answer.createdAt}" type="both" dateStyle="long" timeStyle="short"/>
                      </span>
                    </h5>
                    <h6 class="answer-modified-date-container edited-caption ${(answer.createdAt ne answer.modifiedAt) ? '' : 'hidden'}">
                      <span class="glyphicon glyphicon-edit"></span> <fmt:message key="editedAt"/>
                      <span class="answer-modified-date">
                        <fmt:formatDate value="${answer.modifiedAt}" type="both" dateStyle="long" timeStyle="short"/>
                      </span>
                    </h6>
                  </div>
                  <c:if test="${isAdmin or isAnswerOwner}">
                    <div class="col-xs-12 text-right">
                      <a type="button" class="answer-edit-btn btn btn-custom btn-warn"><fmt:message
                          key="button.edit"/></a>
                      <a type="button" class="answer-delete-btn btn btn-custom btn-dang"><fmt:message
                          key="button.delete"/></a>
                      <a type="button" class="answer-apply-btn hidden btn btn-custom btn-conf"><fmt:message
                          key="button.apply"/></a>
                      <a type="button" class="answer-cancel-btn hidden btn btn-custom btn-warn"><fmt:message
                          key="button.cancel"/></a>
                    </div>
                  </c:if>
                </div>
                <div class="answer-result-alert-container">
                </div>
                <hr>
              </div>
            </div>
          </c:forEach>
        </div>
        <div class="hidden" id="new-answer-template">
          <div class="answer">
            <div class="media">
              <div class="media-body owner-border">
                <p class="answer-text"></p>
                <div class="answer-edit-container hidden">
                  <form class="answer-edit-form">
                    <div class="form-group">
                      <label>
                          <textarea class="answer-text-input form-control" rows="4" name="answer_text"
                                    placeholder="<fmt:message key="answer.text.placeholder"/>"></textarea>
                      </label>
                    </div>
                    <input type="hidden" class="answer-id-input" name="answer_id" value="">
                  </form>
                </div>
                <div>
                  <div class="col-md-6 text-left">
                    <ms:starRating value="0" markCount="0"/>
                  </div>
                  <h5 class="media-heading col-md-6 text-right">
                    <fmt:message key="answer.by"/> <a class="answer-creator-username owner" href="#"></a>
                    <fmt:message key="at"/> <span class="glyphicon glyphicon-time"></span>
                    <span class="answer-created-date create-date"></span>
                  </h5>
                  <h6 class="answer-modified-date-container edited-caption hidden">
                    <span class="glyphicon glyphicon-edit"></span> <fmt:message key="editedAt"/>
                    <span class="answer-modified-date"></span>
                  </h6>
                </div>
                <div class="col-xs-12 text-right">
                  <a type="button" class="answer-edit-btn btn btn-custom btn-warn"><fmt:message key="button.edit"/></a>
                  <a type="button" class="answer-delete-btn btn btn-custom btn-dang"><fmt:message
                      key="button.delete"/></a>
                  <a type="button" class="answer-apply-btn hidden btn btn-custom btn-conf"><fmt:message
                      key="button.apply"/></a>
                  <a type="button" class="answer-cancel-btn hidden btn btn-custom btn-warn"><fmt:message
                      key="button.cancel"/></a>
                </div>
              </div>
              <div class="answer-result-alert-container">
              </div>
              <hr>
            </div>
          </div>
        </div>
        <div class="well">
          <div id="new-answer-form-div"
               class="${((not empty sessionScope.user) and (sessionScope.user.status ne 0) and (not isQuestionOwner) and (empty isAnswerExists)) ? '' : 'hidden'}">
            <form id="new-answer-form">
              <div class="form-group">
                <h4>
                  <label for="new-answer-text-input">
                    <fmt:message key="answer.new.label"/>
                  </label>
                </h4>
                <textarea id="new-answer-text-input" name="new_answer_text" class="form-control" rows="4"
                          placeholder="<fmt:message key="answer.text.placeholder"/>"></textarea>
                <input type="hidden" name="question_id" value="${requestScope.question.id}">
              </div>
              <button type="submit" class="btn btn-primary">
                <fmt:message key="button.submit"/>
              </button>
            </form>
            <div id="new-answer-alert-container"></div>
          </div>
          <div class="text-danger ${sessionScope.user.status eq  0 ? '' : 'hidden'}" id="user-banned-message">
            <fmt:message key="answer.warning.userBanned"/>
          </div>
          <div id="question-owner-message"
               class="${isQuestionOwner  and (sessionScope.user.status ne 0) ? '' : 'hidden'}">
            <fmt:message key="answer.warning.questionOwner"/>
          </div>
          <div id="answer-exists-message" class="${isAnswerExists and (sessionScope.user.status ne 0) ? '' : 'hidden'}">
            <fmt:message key="answer.warning.answerExists"/>
          </div>
          <div id="guest-message" class="${(empty sessionScope.user) ? '' : 'hidden'}">
            <fmt:message key="answer.warning.guest.toLeave"/>
            <a href="<c:url value="/controller?cmd=login&from=${ms:encodeUrl(ms:fullRequestUrl(pageContext.request))}"/>">
              <fmt:message key="answer.warning.guest.link.login"/>
            </a>
            <fmt:message key="answer.warning.guest.or"/>
            <a href="<c:url value="/controller?cmd=register&from=${ms:encodeUrl(ms:fullRequestUrl(pageContext.request))}"/>">
              <fmt:message key="answer.warning.guest.link.register"/>
            </a>
          </div>
        </div>
      </div>
      <jsp:include page="template/footer.jsp"/>
      <script src="<c:url value="/js/lib/bootbox.js"/>"></script>
      <script src="<c:url value="/js/question.js"/>"></script>
      <script src="<c:url value="/js/stars.js"/>"></script>
    </body>
  </html>
</fmt:bundle>