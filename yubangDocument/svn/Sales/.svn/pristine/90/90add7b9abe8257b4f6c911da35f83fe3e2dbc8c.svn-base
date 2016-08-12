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
 <c:forEach var="order" items="${obj.myOrders }">
  <div class="myorder">
    <div class="myorder_top">订单号:${order.orderid }<span class="span1">${order.status}</span></div>
 
    <c:forEach var="orderGood" items="${order.orderGoods }">
    <div class="order_list">
      <img src="${orderGood.imgurl }"/>
      <p>${orderGood.secondarytitle }</p>
      <p><span>¥ ${orderGood.discountprice }</span><span>¥ ${orderGood.originalprice }</span></p>
      <p>X${orderGood.num }</p>
    </div>
    </c:forEach>
    <div class="myorder_bottom">
      <a href="#" class="a1" ontouchstart="this.className='a1-hover'"  ontouchend="this.className='a1'"  ontouchmove="this.className='a1'">${order.nextStatus }</a>
      <span>共<strong>${order.totalGood}</strong>件  实付金额:<strong>¥${order.totalMoney}</strong> </span>
    </div>
  </div>
  </c:forEach>
  
</body>
</html>
