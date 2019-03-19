$(".prev").click(function(){
				var pageCount = $(".page-box ul li").length;
				var first = $(".page-box ul li.first").index()+1;
				
				var last = $(".page-box ul li.last").index()+1;
				
				if(pageCount<=5){//如果总页数<5，直接返回
					
					return;
				}else{
					//隐藏first~last页,下标first-1~last-1
					for(var i=first-1;i<=last-1;i++){
						$($(".page-box ul li")[i]).addClass("hide");
					}
					$($(".page-box ul li")[first-1]).removeClass("first");
					$($(".page-box ul li")[last-1]).removeClass("last");
					if(first-5<=1){
						//显示1~5页，下标0~4
						for(var i=0;i<=4;i++){
							$($(".page-box ul li")[i]).removeClass("hide");
						}
						$($(".page-box ul li")[0]).addClass("first");
						$($(".page-box ul li")[4]).addClass("last");
					}else{
						//显示first-5~last-5，下标first-6~last-6
						for(var i=first-6;i<=last-6;i++){
							$($(".page-box ul li")[i]).removeClass("hide");
						}
						$($(".page-box ul li")[first-6]).addClass("first");
						$($(".page-box ul li")[last-6]).addClass("last");
					}
				}
				
			});
			$(".next").click(function(){
				var pageCount = $(".page-box ul li").length;
				var first = $(".page-box ul li.first").index()+1;
				
				var last = $(".page-box ul li.last").index()+1;
				
				if(pageCount<=5){//如果总页数<5，直接返回
					
					return;
				}else{
					//隐藏first~last页,下标first-1~last-1
					for(var i=first-1;i<=last-1;i++){
						$($(".page-box ul li")[i]).addClass("hide");
					}
					$($(".page-box ul li")[first-1]).removeClass("first");
					$($(".page-box ul li")[last-1]).removeClass("last");
					
					if(last+5>=pageCount){
						//显示pageCount-4~pageCount页，下标pageCount-5~pageCount-1
						for(var i=pageCount-5;i<=pageCount-1;i++){
							$($(".page-box ul li")[i]).removeClass("hide");
						}
						$($(".page-box ul li")[pageCount-5]).addClass("first");
						$($(".page-box ul li")[pageCount-1]).addClass("last");
					}else{
						//显示first+5~last+5，下标first+4~last+4
						for(var i=first+4;i<=last+4;i++){
							$($(".page-box ul li")[i]).removeClass("hide");
						}
						$($(".page-box ul li")[first+4]).addClass("first");
						$($(".page-box ul li")[last+4]).addClass("last");
					}
				}
				
			});
			
			//给页添加事件监听
			$(".page-box ul li").click(function(){
				$(".page-box ul li").removeClass("active");
				$(this).addClass("active");
			});