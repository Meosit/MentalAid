<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="pagecontent" prefix="login.">
  <html lang="${sessionScope.locale}">
    <head>
      <title>Lot list</title>
      <jsp:include page="template/links.jsp"/>
      <link rel="stylesheet" href="<c:url value="/css/lot_list.css"/>">
      <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
    </head>
    <body>
      <jsp:include page="template/navbar.jsp">
        <jsp:param name="isNavbarLess" value="false"/>
      </jsp:include>
      <div class="container">
        <div class="well well-sm">
          <strong>Active lots</strong>
          <div class="btn-group">
            <a href="#" id="list" class="btn btn-default btn-sm">
              <span class="glyphicon glyphicon-th-list"></span> List
            </a>
            <a href="#" id="grid" class="btn btn-default btn-sm">
              <span class="glyphicon glyphicon-th"></span> Grid
            </a>
          </div>
          <div id="custom-search-input">
            <div class="input-group col-md-12">
              <input type="text" class="  search-query form-control" placeholder="Search"/>
              <span class="input-group-btn">
                <button class="btn btn-danger" type="button">
                    <span class=" glyphicon glyphicon-search"></span>
                </button>
              </span>
            </div>
          </div>
        </div>
        <div id="products" class="row list-group">
          <div class="item list-group-item  col-xs-4 col-lg-4">
            <div class="thumbnail">
              <img class="group list-group-image" src="<c:url value="/img/lot_placeholder.jpg"/>" alt=""/>
              <div class="caption">
                <h2 class="group inner list-group-item-heading">
                  Product title long long title super long title</h2>
                <p class="group inner list-group-item-text">
                  Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
                  sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.
                  Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
                  sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.
                  Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
                  sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.
                  Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
                  sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.
                  Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
                  sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.
                  Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
                  sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.
                  Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
                  sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.
                  Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
                  sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.</p>
                <div class="row">
                  <div class="col-xs-12 col-md-6">
                    <p class="lead">Current price: $21.000</p>
                  </div>
                  <div class="col-xs-12 col-md-6">
                    <a class="btn btn-primary" href="#">Bid! $11.500</a>
                    <a class="btn btn-success" href="#">Buy now! $21.000</a>
                  </div>
                  <a class="item-details" href="#">Details</a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <script src="<c:url value="/js/lot_list.js"/>"></script>
    </body>
  </html>
</fmt:bundle>
