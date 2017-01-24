<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ms" uri="http://epam.mksn.by/MentalAid/tag/msTags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="pagecontent" prefix="search_bar.">
  <div class="row">
    <div id="custom-search-input">
      <form class="input-group" action="<c:url value="/controller"/>" method="get">
        <input type="hidden" name="cmd" value="search">
        <input type="hidden" name="baseUrl" value="${ms:baseUrl(pageContext.request)}">
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
      <strong><a href="${ms:baseUrl(pageContext.request)}"
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
</fmt:bundle>