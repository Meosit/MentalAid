<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
  <title>Home | BidBuy</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="<c:url value="/css/lib/bootstrap.css"/>">
  <link rel="stylesheet" href="<c:url value="/css/home.css"/>">
</head>
<body>

<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#nav">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#"><img src="<c:url value="/img/logo.png"/>"></a>
    </div>
    <div class="collapse navbar-collapse" id="nav">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">Home</a></li>
        <li><a href="#"></a></li>
        <li><a href="#">Projects</a></li>
        <li><a href="#">Contact</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="#"><span class="glyphicon glyphicon-plus"></span> Sign up</a></li>
        <li><a href="#"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
      </ul>
    </div>
  </div>
</nav>

<div id="slideshow" class="carousel slide" data-ride="carousel">
  <!-- Indicators -->
  <ol class="carousel-indicators">
    <li data-target="#slideshow" data-slide-to="0" class="active"></li>
    <li data-target="#slideshow" data-slide-to="1"></li>
  </ol>

  <!-- Wrapper for slides -->
  <div class="carousel-inner" role="listbox">
    <div class="item active">
      <img src="https://placehold.it/1200x400?text=IMAGE" alt="Image">
      <div class="carousel-caption">
        <h3>Sell $</h3>
        <p>Money Money.</p>
      </div>
    </div>

    <div class="item">
      <img src="https://placehold.it/1200x400?text=Another Image Maybe" alt="Image">
      <div class="carousel-caption">
        <h3>More Sell $</h3>
        <p>Lorem ipsum...</p>
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
</div><br>

<footer class="container-fluid text-center">
  <p>Footer Text</p>
</footer>

<!-- scripts -->
<script src="<c:url value="/js/lib/jquery-3.1.1.js"/>"></script>
<script src="<c:url value="/js/lib/bootstrap.js"/>"></script>
</body>
</html>

