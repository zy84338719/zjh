$(document).ready(function(){
	var version = document.getElementById("version_id").value;
	var method = document.getElementById("method_id").value;
	var vid = document.getElementById("vid_id").value;
	var vKey = document.getElementById("vKey_id").value;
	
	$("#btn").click(function(){
		$.ajax({
			url:"http://localhost:8080/BdMap/getJson",
			
			success:function(data)
			{
				
				var vData = JSON.stringify(data);
				document.getElementById("result").value = vData;
				parent.jsonObj=vData;
				alert(vData);
			
			},
			error:function(){
				alert('error')
			}
		});
	});
});

/**
 * 提交表单信息
 */
//function locationData() {
////	var url = 'http://117.21.178.22:89/gpsonline/GPSAPI';
////	var xhr = new plus.net.XMLHttpRequest();
//	document.getElementById("result").value = "ajax请求发送中……";
////	var version = document.getElementById("version_id").value;
////	var method = document.getElementById("method_id").value;
////	var vid = document.getElementById("vid_id").value;
////	var vKey = document.getElementById("vKey_id").value;
////	xhr.open("post", url);
//	mui.ajax('http://localhost:8080/BdMap/getJson',
//		{
//			
//			 async: true,
////			data : {
////				version : version,
////				method : method,
////				vid : vid,
////				vKey : vKey
////			},       
// 
//			dataType : 'JSON',
//			
//			type : 'get',
//			timeout : 1000,
//			headers:{'Content-Type':'application/json'},
//			 	
//			success : function(data) {
//				//服务器返回响应，根据响应结果，分析是否登录成功；
//				alert(data);
//				 var result = eval('('+ data +')');    //js原生方法解析json字符串为json对象
//                 document.getElementById("result").value = result.name;
//				 alert(result)
//                 callback(JSON.parse(data));
//                 console.dir("运行成功输出 ：" + result.name);
//                 var vData = JSON.stringify(data);
//                 alert(vData);
//
//			},
//			error : function(xhr, type, errorThrown) {
//				//异常处理；
//				console.log(type);
//			}
//		}
//	)
//}