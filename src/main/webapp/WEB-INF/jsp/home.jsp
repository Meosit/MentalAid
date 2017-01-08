<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ms" uri="http://epam.mksn.by/MentalAid/tag/msTags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="pagecontent" prefix="home.">
  <html lang="${sessionScope.locale}">
  <head>
    <title><fmt:message key="title"/></title>
    <jsp:include page="template/links.jsp"/>
    <link rel="stylesheet" href="<c:url value="/css/home.css"/>">
  </head>
  <body>
  <jsp:include page="template/navbar.jsp">
    <jsp:param name="isNavbarLess" value="false"/>
  </jsp:include>
  <div class="container">
    <div class="row">
      <div id="custom-search-input">
        <div class="input-group">
          <input type="text" class="form-control input-lg" placeholder="<fmt:message key="search.placeholder"/>"/>
          <span class="input-group-btn">
                <button class="btn btn-info btn-lg" type="button">
                    <i class="glyphicon glyphicon-search"></i>
                </button>
              </span>
        </div>
      </div>
    </div>
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
                  <strong>
                    <a ${aClass} href="<c:url value="/controller?cmd=get_question_page&quid=${question.id}"/>"><c:out
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
                <p>
                  <span class="text-nowrap"><span class="glyphicon glyphicon-user"></span> <a ${aClass}
                      href="#">${question.creatorUsername}</a></span>
                  <span class="text-nowrap">| <span class="glyphicon glyphicon-calendar"></span> <fmt:formatDate
                      value="${question.createdAt}" pattern="dd MMMM, yyyy HH:mm"/></span>
                  <span class="text-nowrap">| <span class="glyphicon glyphicon-comment"></span> <a ${aClass}
                      href="<c:url value="/controller?cmd=get_question_page&quid=${question.id}"/>">${question.answerCount} <c:choose>
                    <c:when test="${(question.answerCount % 10) eq 1}">
                      <fmt:message key="question.answers.single"/>
                    </c:when>
                    <c:when test="${((question.answerCount % 10) lt 5) and ((question.answerCount % 10) ne 0)}">
                      <fmt:message key="question.answers.alter"/>
                    </c:when>
                    <c:otherwise>
                      <fmt:message key="question.answers"/>
                    </c:otherwise>
                  </c:choose>
                      </a></span>
                </p>
              </div>
            </div>
            <hr>
          </c:forEach>
        </c:when>
        <c:otherwise>
          <div class="row text-center">
            <h6><fmt:message key="question.nothing"/></h6>
          </div>
        </c:otherwise>
      </c:choose>
    </div>
    <div class="row text-center">
      <c:url value="/controller?cmd=get_home_page&page=" var="baseUrl"/>
      <ms:bootstrapPagination baseUrl="${baseUrl}" currentPage="${requestScope.currentPage}"
                              pageCount="${requestScope.pageCount}"/>
    </div>
  </div>
  <jsp:include page="template/footer.jsp"/>
  </body>
  </html>
</fmt:bundle>
