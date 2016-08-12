<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title></title>
<link rel="stylesheet" type="text/css" href="css/media.css"/>
<link rel="stylesheet" type="text/css" href="css/layout.css"/>
</head>
<body class="body1">
  <a href="#">
    <div class="myfriend">
    <img src="images/f1.png"/>邀请好友<img src="images/arrow.png" class="arrow"/>
    </div>
  </a>
  
  <div class="myfriend_list1">
  <c:forEach var="friend" items="${obj.friendlist }">
    <a href="#">
    <div class="myfriend_list">
      <img src="${friend.headimgurl }"/>
      <img src="images/arrow.png" class="arrow"/>
      <p>${friend.nickname }</p>
      <p><span>微信用户，微信号：${friend.username }</span></p>
    </div>
    </a>
    </c:forEach>
  </div>

</body>
</html>
