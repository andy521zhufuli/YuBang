//页面操作
// slider
function slider () {
		$("#slider").touchSlider({
		flexible : true,
		speed : 500,
		btn_prev : $("#btn_prev"),
		btn_next : $("#btn_next"),
		paging : $(".nums li"),
		counter : function (e) {
			$(".nums li").removeClass("current").eq(e.current-1).addClass("current");
		}
	});
	$("#slider").bind("touchstart", function() {
		clearInterval(timer);
	}).bind("touchend", function() {
		timer = setInterval(function() { $("#btn_next").click();}, 5000);
	});
	timer = setInterval(function() { $("#btn_next").click();}, 5000);
	$("#slider").hover(function() {
		clearInterval(timer);
	}, function() {
		timer = setInterval(function() { $("#btn_next").click();}, 5000);
	})
}
//tab
window.onload=function(){
//	tabNews();
  
	slider();
	tabColor();
	maintab();
}
//隔行
function tabColor(){
	var lis = document.getElementsByTagName("li"); 
	var list;
	for(var i = 0; i < lis.length;i++){ 
		if(lis[i].className == "listOne")
			{list=document.getElementsByClassName("listOne");listTable();continue;}
		else if (lis[i].className == "listTwo")
			{list=document.getElementsByClassName("listTwo");listTable();continue;}
		else if (lis[i].className == "listThree")
			{list=document.getElementsByClassName("listThree");listTable();continue;}
		else if (lis[i].className == "listFour")
			{list=document.getElementsByClassName("listFour");listTable();continue;}
		else if (lis[i].className == "listFive")
			{list=document.getElementsByClassName("listFive");listTable();continue;}
		else if (lis[i].className == "listSix")
			{list=document.getElementsByClassName("listSix");listTable();}	
	} 
function listTable(){
		if (list)
		{
			for (j=0;j<list.length ;j++ )
			{if (j%2 == 0)
				{list[j].style.backgroundColor="#f3f3f3";}
			}
		}
	}
}
/*maintab*/
function maintab(){
	var mainNav=document.getElementById("mainNav");
	if (mainNav)
	{
	var mainMenu=mainNav.getElementsByTagName("li");
	var wraps=document.getElementsByClassName("innerWrap");
		for (i=0;i<mainMenu.length; i++) {
			wraps[i].style.display="none";
			wraps[0].style.display="block";
			(function () {
				var j = i
				mainMenu[j].onclick = (function () {
					stab(this);
					wraps[j].style.display="block";
				 })
			 })();
		 }
	}
	 function stab(elem){
			for (var i = 0; i < mainMenu.length; i++) {
				mainMenu[i].className = mainMenu[i].className.replace('on', ' ');
				wraps[i].style.display = "none";
				}
				elem.className = 'on';
		 }
}
//滑动切换
function tabNews(){
	var menus = document.getElementsByClassName("menu"); 
	var contents = document.getElementsByClassName("menuContent");
		for(var i = 0;i < menus.length;i++){    
			  var menu = menus[i];          		  
			  var content = contents[i];
			  menu.style.display="block";
			  content.style.display="block";
			  tab(content,menu)
		};	
}
function tab(content,menu){
	var tabs = new Swipe(content,
						{callback: function(event, index, elem){setTab(selectors[index]);}}),
						selectors = menu.children;
	for (var i = 0; i<selectors.length; i++) {
		var elem = selectors[i];
		elem.setAttribute('data-tab', i);
		elem.onclick = function(e) {
			e.preventDefault();setTab(this);tabs.slide(parseInt(this.getAttribute('data-tab'), 10), 300);
		}
	}
	function setTab(elem) {
		for (var i = 0; i < selectors.length; i++) {
			selectors[i].className = selectors[i].className.replace('on', ' ');}
		elem.className += ' on';
	}
}

//下拉菜单
function showMenu(){
	var up=document.getElementById("up");
	var showMore=document.getElementById("showMore");
	if (showMore.style.display=="none")
		{showMore.style.display="block";
		up.className="up";}
	else
		{showMore.style.display="none";
		up.className="";}
}


function next_page(){
	slider.next();
}

function prev_page(){
	slider.prev();
}
