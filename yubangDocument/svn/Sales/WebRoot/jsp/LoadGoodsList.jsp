<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title></title>
<%@ include file="base/base.jsp"%>
<link rel="stylesheet" type="text/css" href="jsp/css/media.css"/>
<link rel="stylesheet" type="text/css" href="jsp/css/layout.css"/>
<script type="text/javascript">
function clickGoodsDestail(goodsid){
window.callClient.goodsListItemClick(goodsid);
}

</script>
</head>
<body>
<c:forEach var="goods" items="${obj.goodslist}">
  <div class="list"  onclick="clickGoodsDestail(${goods.goodsid})">
    <img src="${goods.imgurl}"/>
    <p class="p1">${goods.title}</p>
    <p class="p2">￥${goods.discountprice}<span>￥${goods.originalprice}</span><span>12片/盒</span></p>
  </div>
  </c:forEach>

</body>
</html>
