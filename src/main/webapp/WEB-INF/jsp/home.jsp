<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
  <div>


    <div id="slideshow" class="carousel slide" data-ride="carousel">
      <!-- Indicators -->
      <ol class="carousel-indicators">
        <li data-target="#slideshow" data-slide-to="0" class="active"></li>
        <li data-target="#slideshow" data-slide-to="1"></li>
        <li data-target="#slideshow" data-slide-to="2"></li>
      </ol>

      <!-- Wrapper for slides -->
      <div class="carousel-inner" role="listbox">
        <div class="item active">
          <img src="<c:url value="/img/overview-bid.jpg"/>" alt="Bid Image">
          <div class="carousel-caption">
            <h1><fmt:message key="slideshow.bid.title"/></h1>
            <h3><fmt:message key="slideshow.bid.summary"/></h3>
          </div>
        </div>

        <div class="item">
          <img src="<c:url value="/img/overview-handshake.jpg"/>" alt="Deal Image">
          <div class="carousel-caption">
            <h1><fmt:message key="slideshow.buy.title"/></h1>
            <h3><fmt:message key="slideshow.buy.summary"/></h3>
          </div>
        </div>

        <div class="item">
          <img src="<c:url value="/img/overview-sell.jpg"/>" alt="Sell Image">
          <div class="carousel-caption">
            <h1><fmt:message key="slideshow.sell.title"/></h1>
            <h3><fmt:message key="slideshow.sell.summary"/></h3>
          </div>
        </div>
      </div>

      <!-- Left and right controls -->
      <a class="left carousel-control" href="#slideshow" role="button" data-slide="prev">
        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
      </a>
      <a class="right carousel-control" href="#slideshow" role="button" data-slide="next">
        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
      </a>
    </div>

    <div class="container text-center">
      <h3>What We Do</h3><br>
      <div class="row">
        <div class="col-sm-4">
          <img src="https://placehold.it/150x80?text=IMAGE" class="img-responsive" style="width:100%" alt="Image">
          <p>Current Project</p>
        </div>
        <div class="col-sm-4">
          <img src="https://placehold.it/150x80?text=IMAGE" class="img-responsive" style="width:100%" alt="Image">
          <p>Project 2</p>
        </div>
        <div class="col-sm-4">
          <div class="well">
            <p>Some text..</p>
          </div>
          <div class="well">
            <p>Some text..</p>
          </div>
        </div>
      </div>
    </div>
  </div>
  <jsp:include page="template/footer.jsp"/>
  </body>
  </html>
</fmt:bundle>
