<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ms" uri="http://epam.mksn.by/MentalAid/tag/msTags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="pagecontent" prefix="question_list.">
  <div class="row">
    <div id="custom-search-input">
      <form class="input-group" action="<c:url value="/controller"/>" method="get">
        <input type="hidden" name="cmd" value="search">
        <input type="hidden" name="baseUrl" value="${ms:fullRequestUrl(pageContext.request)}">
        <c:choose>
          <c:when test="${not empty param.query}">
            <input type="text" name="query" class="form-control input-lg"
                   placeholder="<fmt:message key="search.placeholder"/>"
                   value="<c:out value="${param.query}"/>">
          </c:when>
          <c:otherwise>
            <input type="text" name="query" class="form-control input-lg"
                   placeholder="<fmt:message key="search.placeholder"/>">
          </c:otherwise>
        </c:choose>
        <span class="input-group-btn">
          <button class="btn btn-info btn-lg" type="submit">
              <i class="glyphicon glyphicon-search"></i>
          </button>
        </span>
      </form>
    </div>
  </div>
  <c:if test="${not empty param.query}">
    <c:set var="resultCount" value="${fn:length(requestScope.questions)}"/>
    <div class="row search-info">
      <strong><a href="${ms:fullRequestUrl(pageContext.request)}"
                 class="media-bottom btn btn-custom btn-dang"><fmt:message key="search.reset"/></a>
        <c:choose>
          <c:when test="${resultCount eq 1}">
            <fmt:message key="search.message.single"><fmt:param value="${resultCount}"/></fmt:message>
          </c:when>
          <c:when test="${(resultCount % 10) eq 1}">
            <c:choose>
              <c:when test="${sessionScope.locale eq 'ru'}}">
                <fmt:message key="search.message.single"><fmt:param value="${resultCount}"/></fmt:message>
              </c:when>
              <c:otherwise>
                <fmt:message key="search.message.multiple"><fmt:param value="${resultCount}"/></fmt:message>
              </c:otherwise>
            </c:choose>
          </c:when>
          <c:when test="${((resultCount % 10) lt 5) and ((resultCount % 10) ne 0)}">
            <fmt:message key="search.message.alter"><fmt:param value="${resultCount}"/></fmt:message>
          </c:when>
          <c:otherwise>
            <fmt:message key="search.message.multiple"><fmt:param value="${resultCount}"/></fmt:message>
          </c:otherwise>
        </c:choose>
        <i>"<c:out value="${param.query}"/>"</i></strong>
    </div>
  </c:if>
  <div>
    <c:choose>
      <c:when test="${not empty requestScope.questions}">
        <c:forEach var="question" items="${requestScope.questions}">
          <c:choose>
            <c:when test="${(not empty sessionScope.user) and (sessionScope.user.id eq question.creatorId)}">
              <c:set var="aClass" value="class='owner'"/>
            </c:when>
            <c:otherwise>
              <c:set var="aClass" value="class=''"/>
            </c:otherwise>
          </c:choose>

          <div class="question">
            <div>
              <h4>
                <strong><a ${aClass}
                    href="<c:url value="/controller?cmd=question&quid=${question.id}&from=${requestScope.fullUrl}"/>"><c:out
                    value="${question.title}"/></a>
                </strong>
              </h4>
            </div>
            <div>
              <p class="fade-long">
                <c:out value="${question.description}"/>
              </p>
            </div>
            <div>
              <p><span class="text-nowrap"><span class="glyphicon glyphicon-user"></span> <a ${aClass}
                  href="#">${question.creatorUsername}</a></span>
                <span class="text-nowrap">| <span class="glyphicon glyphicon-calendar"></span> <fmt:formatDate
                    value="${question.createdAt}" pattern="dd MMMM, yyyy HH:mm"/></span>
                <span class="text-nowrap">| <span class="glyphicon glyphicon-comment"></span> <a ${aClass}
                    href="<c:url value="/controller?cmd=question&quid=${question.id}&from=${requestScope.fullUrl}#answers"/>">${question.answerCount} <c:choose>
                  <c:when test="${question.answerCount eq 1}">
                    <fmt:message key="question.answers.single"/>
                  </c:when>
                  <c:when test="${(question.answerCount % 10) eq 1}">
                    <c:choose>
                      <c:when test="${sessionScope.locale eq 'ru'}}">
                        <fmt:message key="question.answers.single"/>
                      </c:when>
                      <c:otherwise>
                        <fmt:message key="question.answers.multiple"/>
                      </c:otherwise>
                    </c:choose>
                  </c:when>
                  <c:when test="${((question.answerCount % 10) lt 5) and ((question.answerCount % 10) ne 0)}">
                    <fmt:message key="question.answers.alter"/>
                  </c:when>
                  <c:otherwise>
                    <fmt:message key="question.answers.multiple"/>
                  </c:otherwise>
                </c:choose></a></span>
              </p>
            </div>
          </div>
          <hr>
        </c:forEach>
      </c:when>
      <c:otherwise>
        <div class="row text-center">
          <h3><fmt:message key="question.nothing"/></h3>
        </div>
      </c:otherwise>
    </c:choose>
  </div>
  <c:if test="${not empty param.question_deleted}">
    <div class="alert-fixed alert alert-success alert-dismissable">
      <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
      <strong><fmt:message key="question.deleted.title"/></strong> <fmt:message key="question.deleted.message"/>
    </div>
  </c:if>
  <div class="row text-center">
    <ms:bootstrapPagination baseUrl="${ms:fullRequestUrl(pageContext.request)}"
                            currentPage="${requestScope.currentPage}" pageCount="${requestScope.pageCount}"/>
  </div>
</fmt:bundle>