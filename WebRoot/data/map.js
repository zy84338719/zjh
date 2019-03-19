/**
 * 
 */


<script type="text/javascript">
	$(document).ready(function() {
		var version = document.getElementById("version_id").value;
		var method = document.getElementById("method_id").value;
		var vid = document.getElementById("vid_id").value;
		var vKey = document.getElementById("vKey_id").value;
		$.ajax({
			url : "http://localhost:8080/BdMap/getJson",
			data: {
				version:version,
				method:method,
				vid:vid,
				vKey:vKey
			},
			success : function(data) {

				point(JSON.parse(data));

			},
			error : function() {
				alert('error')
			}
		});
	});
</script>
<script type="text/javascript">
	function point(jsonObj) {
		alert(jsonObj);


		// 百度地图API功能
		var map = new BMap.Map("container");


		<!--jsonObj.locs[0].-->
		translateCallback = function(data) {
			if (data.status === 0) {
				var marker = new BMap.Marker(data.points[0]);
				map.addOverlay(marker);
				var label = new BMap.Label("车辆ID : " + jsonObj.locs[0].id + " <br/> 车辆状态 : " + jsonObj.locs[0].state + " <br/> 油量 : " +
					jsonObj.locs[0].oil + "  <br/> 速度 : " + jsonObj.locs[0].speed + " <br/>行驶里程 : " + jsonObj.locs[0].distance +
					" <br/> 不在线时长（分钟） : " + jsonObj.locs[0].vhcofflinemin +
					"<br/>位置 ： " + jsonObj.locs[0].info.substr(8) + "", {
						offset : new BMap.Size(50, -10)
					});
				marker.setLabel(label); //添加百度label
				map.centerAndZoom(data.points[0], 9);
			}
		}

		map.centerAndZoom(new BMap.Point(118.454, 32.955), 6);
		map.enableScrollWheelZoom();
		var zhangjiajie = new BMap.Point(110.484901, 29.122586),
			jishoushi = new BMap.Point(109.704591, 28.268536),
			guzhangxian = new BMap.Point(109.957373, 28.623036),
			carPosition = new BMap.Point(jsonObj.locs[0].lng, jsonObj.locs[0].lat),
			mayangxian = new BMap.Point(109.808998, 27.871879),
			huaihuashi = new BMap.Point(110.008103, 27.575648);
		var points = [ zhangjiajie, guzhangxian, jishoushi, mayangxian, huaihuashi ];

		var curve = new BMapLib.CurveLine(points, {
			strokeColor : "blue",
			strokeWeight : 3,
			strokeOpacity : 0.5
		}); //创建弧线对象
		map.addOverlay(curve); //添加到地图中

		curve.enableEditing(); //开启编辑功能

		var convertor = new BMap.Convertor();
		var pointArr = [];
		pointArr.push(carPosition);
		convertor.translate(pointArr, 1, 5, translateCallback);
	}
</script>