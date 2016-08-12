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
  <div class="take_detail">
    <div class="take_num">提现单号<span>${obj.cashId }</span></div>
    <div class="take_tip1">
      提现金额<span>￥${obj.amount }</span>
      <div class="take_tip">
        <p>银行已划扣</p>
        <p>请注意查收</p>
      </div>
    </div>
    <div class="bank">
      <p><span>账&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;户</span><span>${obj.acountName }</span></p>
      <p><span>银&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;行</span><span>${obj.bank }</span></p>
      <p><span>账&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号</span><span>${obj.acount }</span></p>
      <p><span>交易单号</span><span class="span1">${obj.tradId }</span></p>
    </div>
  </div>
  <div class="order_title">订单处理记录</div>
  <div class="order_history">
  <c:forEach var="orderDealHis" items="${obj.orderDealHisList }">
   <p><span>${orderDealHis.timeStamp }</span><span>${orderDealHis.user }</span>${orderDealHis.info }</span></p>
  </c:forEach>
  </div>
</body>
</html>
