<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<script type="text/javascript">
$(document).ready(
function (){
$($(".head")[0]).css("margin-left",(screen.width-860)/2);
$('.head').bind('onmouseout',reset);
$('.item').hover(function() {
change(this);
});
}
);
function change(span){
var size=$('.item').size();
var item=$('.item');
for(var i=0;i<size;i++){
$(item[i]).css("background-image","url('comom/base/globalsearch_bg.png')");
}
$(span).css("background-image","url('comom/base/ok.png')");

}
function reset(){
change($('.item')[8]);
}
</script>

        <div class="head" onmouseout="reset()">
<nav>
    <a  href="index.jsp"><span class="item"><span class="item_font">首页</span></span></a>
     <a href="jsp/HealthPaipai.jsp"><span class="item">健康拍拍</span></a>
      <a href="jsp/PhysicalTest.jsp"><span class="item">健康评测</span></a>
       <a><span class="item">干预方案</span></a>
        <a><span class="item">体检中心</span></a>
         <a><span class="item">健康百科</span></a>
         <a><span class="item">健康服务</span></a>
         <% String userid = (String)( session.getAttribute("userid")); %>
         <a id="menu_loginachor"<% if (userid == null ) {%> onclick="loginWithArtDialog()"<% } %> ><span id="menu_login" class="item"><% if (userid == null ) {%> 登录 <% } else { %> 欢迎您！<%} %></span></a>
</nav>
    </div>