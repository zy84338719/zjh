var server_path = "";

//顶部导航栏事件监听
$(".top_nav ul li").click(function() {
	//[1]变更自身颜色
	$(".top_nav ul li").css("background", "#495B69");
	$(this).css("background", "#1C242C");
	
	//[2]变更当前位置
	$(".location span:nth-of-type(1)").text($(this).children("a").text());
	$(".location span:nth-of-type(2)").text("");

	//[3]变更左侧导航栏调目
	var left_nav = $(".left-nav ul");
	var index = $(this).index();
	alert(index)
	switch(index) {
		case 0: //首页
			left_nav.html('');
			left_nav.append('<li><a href="#"><span>-</span>使用指南</a></li>');
			left_nav.append('<li><a href="#"><span>-</span>消息通知</a></li>');
			break;
		case 1: //用户管理
			left_nav.html('');
			left_nav.append('<li><a href="../user/queryUsers.action"><span>-</span>用户查看</a></li>');
			left_nav.append('<li><a href="../user/queryUsers.action?option=authen"><span>-</span>认证申请</a></li>');
			break;
		case 2: //简历管理
			left_nav.html('');
			left_nav.append('<li><a href="/zhutongtianxia/admin/resume/query.html"><span>-</span>简历查看</a></li>');
			left_nav.append('<li><a href="/zhutongtianxia/admin/resume/verifyreq.html"><span>-</span>简历审核</a></li>');
			left_nav.append('<li><a href="/zhutongtianxia/admin/resume/evaluatereq.html"><span>-</span>简历估价</a></li>');
			left_nav.append('<li><a href="#"><span>-</span>简历删除</a></li>');
			break;
		case 3: //资质管理
			left_nav.html('');
			left_nav.append('<li><a href="/zhutongtianxia/admin/qualification/query.html"><span>-</span>资质查看</a></li>');
			left_nav.append('<li><a href="/zhutongtianxia/admin/qualification/verifyreq.html"><span>-</span>资质审核</a></li>');
			left_nav.append('<li><a href="#"><span>-</span>资质估价</a></li>');
			left_nav.append('<li><a href="#"><span>-</span>资质删除</a></li>');
			break;
		case 4://招聘管理
			left_nav.html('');
			left_nav.append('<li><a href="/zhutongtianxia/admin/recruit/query.html"><span>-</span>招聘查看</a></li>');
			left_nav.append('<li><a href="/zhutongtianxia/admin/recruit/verifyreq.html"><span>-</span>招聘审核</a></li>');
			left_nav.append('<li><a href="#"><span>-</span>招聘删除</a></li>');
			break;
		case 5:
			left_nav.html('');
			left_nav.append('<li><a href="/zhutongtianxia/admin/cart/query.html"><span>-</span>购物车查看</a></li>');
			left_nav.append('<li><a href="#"><span>-</span>购物车删除</a></li>');
			break;
		case 6:
			left_nav.html('');
			left_nav.append('<li><a href="/zhutongtianxia/admin/order/query.html"><span>-</span>订单查看</a></li>');
			left_nav.append('<li><a href="#"><span>-</span>我的受理</a></li>');
			left_nav.append('<li><a href="/zhutongtianxia/admin/order/acceptreq.html"><span>-</span>订单受理</a></li>');
			left_nav.append('<li><a href="#"><span>-</span>订单更改</a></li>');
			left_nav.append('<li><a href="#"><span>-</span>订单删除</a></li>');
			break;
		case 7:
			//论坛管理
			left_nav.html('');
			left_nav.append('<li><a href="../topic/queryTopics.action"><span>-</span>帖子查看</a></li>');
			left_nav.append('<li><a href="../topic/queryTopics.action?option=verify"><span>-</span>帖子审核</a></li>');
			left_nav.append('<li><a href="#"><span>-</span>帖子删除</a></li>');
			break;
		default:
			left_nav.html('');
	}
	
	//[4]清空右侧内容区
	$(".right-main").html("");
});

//退出登录监听
var M = {};
function logout(){
	if (M.logoutDailog) {
		return M.logoutDailog.show();
	}else{
		M.logoutDailog = jqueryAlert({
			'title'   : '操作提示',
			'content' : '确定要退出登录吗？',
			'modal'   : true,
			'buttons' :{
				'确定' : function(){
					location.href = "../manager/logout.action";
					
				},
				'取消' : function(){
					M.logoutDailog.close();
				}
			}
		});
	}
	
}

//显示插件提示
function showToast(msg, icon) {
	M.dialog = jqueryAlert({
		'icon' : '../alert/img/' + icon,
		'content' : msg,
		'closeTime' : 3000,
	});
}
