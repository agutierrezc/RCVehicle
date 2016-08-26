<!DOCTYPE html>
<html ng-app="app">
<head>
<title></title>

<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.19/angular.min.js"></script>

<script type="text/javascript" src="js/js.js"></script>

<link rel="stylesheet" type="text/css" href="css/css.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7"
	crossorigin="anonymous">

</head>
<body class="body" ng-controller="tapController as tab"
	ng-init="tab.initPage()">
	<header>
		<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="collapse navbar-collapse">
					<ul class="nav navbar-nav">
						<li class="dropdown">
							<button type="button" class="btn btn-default">Mapa</button>
							<ul class="dropdown-menu">
								<li>
									<div id="grid-div" class="grid-div"
										ng-mousemove="tab.mousePos($event)" ng-click="tab.configMap()">
									</div>
								</li>
							</ul>
						</li>
						<li class="dropdown">
							<button type="button" class="btn btn-default" ng-click="tab.restartPosition()">Reiniciar
								Posicion</button>
						</li>
						<li class="dropdown">
							<button type="button" class="btn btn-default" ng-click="tab.locateVehicle()">Ubicar
								Vehiculo</button>
						</li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li></li>
						<li>
							<div class="compass">
								<img src="img/brujula.png" width="50" height="50">
							</div>
						</li>
					</ul>
				</div>
			</div>
		</nav>
	</header>
	<section>
		<div class="div body-div">
			<div id="map" class="map"></div>
			<div class="console-div">
				<div class="consoleLog">
					<textarea id="txtConsole" class="txtConsole" readonly>{{tab.comandLog}}</textarea>
				</div>

				<div class="consoleInput">
					<input class="txtConsoleInput" type="text"
						placeholder="Escriba el comando aqui" ng-model="tab.comand"
						ng-keypress="$event.keyCode == 13 && tab.sendComand()">
				</div>
			</div>

		</div>
	</section>

	<footer> </footer>

</body>
</html>
