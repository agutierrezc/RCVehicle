var app = angular.module('app', [])

app.controller("tapController", function($http, $scope) {

	var tapController = this;
	


	tapController.comand = "";
	tapController.comandLog = "";
	
	tapController.axisX = 1;
	tapController.axisY = 1;
	tapController.vehiclePosition = {
			'x':0, 
			'y':0};
	
	
	this.mousePos = function(event){
	
		//Por performace no conviene que pase de 20X20
		if (event.clientX <= 420 && event.clientY <= 440) {

			var cantXTemp = Math.ceil(event.clientX/20)-1;
			var cantYTemp = Math.ceil((event.clientY-50)/20);
			
			var innerHTML = "";
			var img ="<img src='img/isSele.png' ng-click='tab.configMap()'>";
			
			var imgHTML = "";
			
			if(cantXTemp != tapController.axisX || cantYTemp != tapController.axisY) {
				
				for (var i = 0; i < cantXTemp; i++) {
					imgHTML = imgHTML + img;
				}
				
				innerHTML = imgHTML;
				
				for (var i = 0; i < cantYTemp-1; i++) {
					innerHTML = innerHTML + "<br>" + imgHTML;
				}
				
				tapController.axisX = cantXTemp;
				tapController.axisY = cantYTemp;
				
				innerHTML = innerHTML +" <br><br> n="+cantYTemp+"<br>m="+cantXTemp;
				
				document.getElementById("grid-div").innerHTML = innerHTML;
				document.getElementById("grid-div").style.width = ((cantXTemp + 2) * 20) + "px";
				document.getElementById("grid-div").style.height = ((cantYTemp + 4) * 20) + "px";
			}
		}
	};
	
	this.configMap = function() {
		
		var mapPoint = {
			x: tapController.axisX,
			y: tapController.axisY
		};
		
		
		tapController.printCommand("COMANDO", "Map " + tapController.axisY + "," + tapController.axisX);
		
		$http.post('/RCVBackEnd/v1/Map', mapPoint, { 
			headers : {
				Accept: "text/plain",
				'Content-Type' : "application/json"
			}
		}).success(function(data) {
			tapController.restartPosition();
		}).error(function(data, status){
			if(status == "406"){
				tapController.printCommand("ERROR", data);
			}
		});
	};
	
	this.moveVehicle = function(commans){
		tapController.printCommand("COMANDO", tapController.comand);
		$http.post('/RCVBackEnd/v1/moveVehicle', commans, 
				{headers : {
					Accept: "text/plain",
					'Content-Type' : "text/plain"
				}
		}).success(function(data) {
			var res = data.split(",");
			tapController.vehiclePosition.x = res[1];
			tapController.vehiclePosition.y = res[0];
			tapController.drawMap();
		}).error(function(data, status){
			if(status == "406"){
				tapController.printCommand("ERROR", data);
			}
		});
	};
	
	this.drawVehicle = function(){
		var divId = tapController.vehiclePosition.y + "-" + tapController.vehiclePosition.x;
		document.getElementById(divId).innerHTML = "X";
	};
	
	this.initPage = function(){
		
		$http.get('/RCVBackEnd/v1/getPosition', 
				{headers : {
					Accept: "text/plain",
					'Content-Type' : "text/plain"
					}
				}).success(function(data) {
			
			var res = data.split(",");
			tapController.vehiclePosition.x = res[1];
			tapController.vehiclePosition.y = res[0];
			$http.get('/RCVBackEnd/v1/Map').success(function(data) {
				tapController.axisX = data.axisX;
				tapController.axisY = data.axisY;
				tapController.drawMap();
			});
		});
		
	}
	
	this.getMap = function(){
		tapController.printCommand("COMANDO", "Map");
		$http.get('/RCVBackEnd/v1/Map').success(function(data) {
			tapController.printCommand("MAP", "n=" + data.axisY + " m=" + data.axisX);
		});
	}

	this.drawMap = function(){
		
		$http.get('/RCVBackEnd/v1/Map').success(function(data) {
			tapController.axisX = data.axisX;
			tapController.axisY = data.axisY;
			
			var col = "";
			var row = "";
			var map="";
			
			for (var n = 0; n < tapController.axisY; n++) {
				for(var m = 0; m < tapController.axisX; m++){
					var divId = n + "-" + m
					col = col + "<td><div id='" + divId + "' class = 'square' title= '" + divId + "'></div></td>";
				}
				row = "<tr>"+col+"</tr>" + row;
				col = "";
			}
			map = row;
			
			document.getElementById("map").innerHTML ="<div class='map-draw'><table>"+map+"</table></div>";
			tapController.drawVehicle();
		});
	};
	
	this.restartPosition = function(){
		$http['delete']("/RCVBackEnd/v1/restartPosition", 
			{headers : {
				Accept: "text/plain",
				'Content-Type' : "text/plain"
				}
			}).success(function(data) {
				tapController.printCommand("COMANDO", "Restart");
				tapController.initPage();
			});
	};
	
	this.locateVehicle = function(){
		tapController.printCommand("COMANDO", "Locate");
		$http.get('/RCVBackEnd/v1/getPosition', 
				{headers : {
					Accept: "text/plain",
					'Content-Type' : "text/plain"
					}
				}).success(function(data) {
					var res = data.split(",");
					tapController.vehiclePosition.x = res[1];
					tapController.vehiclePosition.y = res[0];
					var position = "n:" + tapController.vehiclePosition.y + ", m:" + tapController.vehiclePosition.x;
					var divId = tapController.vehiclePosition.y + "-" + tapController.vehiclePosition.x;
					document.getElementById(divId).style.background = "red";
					tapController.printCommand("VEHICULO", position);
				});
	};
	
	this.sendComand = function(){
		 if(tapController.comand !=" " && tapController.comand !=""){
			 if(tapController.comand == "Locate"){
				 tapController.locateVehicle();
			 } else if(tapController.comand == "Map"){
				 tapController.getMap();
			 } else if(tapController.comand.startsWith("Map ")){
				 var comand = tapController.comand.split(" ");
				 var res = comand[1].split(",");
				 tapController.axisX = res[1];
				 tapController.axisY = res[0];
				 tapController.configMap();
			 } else if(tapController.comand == "Restart"){
				 tapController.restartPosition();
			 } else {
				 tapController.moveVehicle(tapController.comand);
			 }
			 tapController.comand =" ";
		 }
		 var d = document.getElementById("txtConsole");
		 d.scrollTop = d.scrollHeight;
	};
	
	this.printCommand = function(type, command){
		tapController.comandLog = tapController.comandLog + type + ": " + command +"\n";
		var d = document.getElementById("txtConsole");
		d.scrollTop = d.scrollHeight;
	}
});