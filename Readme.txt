Elaborado por: Andres Felipe Gutierrez Camacho
Identificado con CC: 1019011235
----------------------------------------------

-----------------------------------------------------------------------------------
------------------------- Indicaciones Generales ----------------------------------
-----------------------------------------------------------------------------------

La aplicación se desarrollo sobre Eclipse Mars 2.0 usando JDK 7 y Maven 3.3.9
El proyecto principal se llama RCVehicle y contiene tres modulos Maven:

	1. RCVConsole: aplicación por consola (parte 1 del ejercicio)
	2. RCVBackEnd: aplicación back-end (parte 2 del ejercicio)
	3. RCVFrontEnd: aplicación front-end (parte 2 del ejercicio)

Todos los módulos tiene en común las librerías Spring framwork 4.3 y JUnit 4.12.
Para ejecutar las pruebas unitarias sin necesidad de importar el proyecto, es 
necesario instalar Maven 3.3.9 o superior. Desde la consola, dirigirse a la carpeta
del proyecto y ejecutar el comando "mvn test". Puede fallar las dos o tres primeras
veces ya que maven descarga las librerías necesarias y si no están listas genera
error de compilación.

De igual forma si se desea generar los archivos .jar o .war de los modulos, es 
necesario dirigirse a la carpeta de modulo y ejecutar el comando "mvn package". 
Después de terminar se genera una carpeta con el nombre Target donde se podrá 
encontrar el archivo deseado.


-----------------------------------------------------------------------------------
---------------------------- Instrucciones RCVConsole -----------------------------
-----------------------------------------------------------------------------------

Precondiciones: Java 1.7 o superior, verificar ejecutando por consola el 
comando "java -version".

Como ejecutarla: Después de ejecutar el comando "mvn package", dirigirse a la carpeta
target y ejecutar el siguiente comando "java -jar RCVConsole-0.0.1.jar"

Como interactuar: Una vez ejecutado el programa, este brinda la oportunidad de salir
en cualquier momento presionando la letra "q" y enter.

Lo primero que se debe hacer es enviar las dimensiones del map o superficie de 
desplazamiento en formato n,m. Ej: 4,3 esto quiere decir que el vehículo podrá 
moverse hasta tres (3) posiciones hacia el norte y hasta dos(2) al este, esto ya que
el vehículo inicia en la posición 0,0.

Una vez configurado el mapa se pueden enviar comandos en formato 
<Desplazamiento>,<Dirección>. Ej: 10,N lo que quiere decir que el vehículo se moverá
10 pasos en dirección norte.

De igual forma se pueden enviar varios comando al mismo tiempo separándolos por con
el carácter punto y coma (;). Ej: 10,N;7,S;3,E;1,O  Si el alguno de los comandos 
hace que el vehículo salga del mapa no se ejecutaran los siguientes comandos.

Cada vez que se ejecuta un comando la aplicación muestra el comando a ejecutar y
la nueva posición del vehículo, siempre y cuando no exceda los limites del mapa. Al 
finalizar la ejecucion de los comandos se muestra la posicion final del vehiculo.


-----------------------------------------------------------------------------------
---------------------------- Instrucciones RCVBackEnd -----------------------------
-----------------------------------------------------------------------------------

Precondiciones: Java 1.7 o superior, verificar ejecutando por consola el 
comando "java -version".
				Tomcat 7.0.69 o superior.

Como instalar: En la carpeta del modulo, ejecutar el comando "mvn package" para 
generar el archivo .war. Una vez generado y siempre y cuando se tenga la variable 
de entorno "CATALINA_HOME" con la ruta del Tomcat 7 debidamente seteada, ejecutar
el comando "mvn cargo:deploy". Si no se tiene la variable, copiar el archivo 
"RCVBackEnd.war" dentro de la carpeta "webapps" del Tomcat 7.

El modulo RCVBackEnd es una aplicación web la cual expone los servicios REST con 
los que el modulo RCVFrontEnd se comunica para llevar acabo las instrucciones.

La instalación de el modulo RCVFrontEnd se debe realizar en el mismo servidor donde
esta el modulo RCVBackEnd.

Este modulo expone cinco (5) servicios REST:

	1. "RCVBackEnd/v1/Map"; POST: Servicio que recibe dos parámetros en el header
	"n y m", se encarga de crear el map y retorna en formato JSON las dimensiones
	del mapa. Si se envían números negativos retorna un mensaje en formato text
	que indica que las dimensiones son incorrectas y código 406.

	2. "RCVBackEnd/v1/Map"; GET: Servicio que retona en formato JSON las dimensiones
	del mapa.

	3. "RCVBackEnd/v1/moveVehicle"; POST: Servicio que se encarga de mover el
	vehículo siempre y cuando los parámetros recibidos cumplan las mismas condiciones
	que se especifican para el modulo RCVConsole. Si los comandos no cumple la 
	estructura o el vehículo saldría del mapa el servicio retorna un error con
	código 406 Not Acceptable, y el mensaje según el error. Si retorna un código
	500  Internal Server Error es por que al ejecutar el programa se presento un
	error no controlado. Retorna la ultima posición del vehículo.

	4. "RCVBackEnd/v1/getPosition"; GET: Servicio que retorna la ubicación actual 
	del vehiculo.

	5. "RCVBackEnd/v1/restartPosition"; DELETE: Servicio que reinicia la posición
	del vehículo, lo deja en la posición 0,0 y retorna un String con la posición.


-----------------------------------------------------------------------------------
---------------------------- Instrucciones RCVFrontEnd ----------------------------
-----------------------------------------------------------------------------------

Precondiciones: Java 1.7 o superior, verificar ejecutando por consola el 
comando "java -version".
				Tomcat 7.0.69 o superior.

Como instalar: En la carpeta del modulo, ejecutar el comando "mvn package" para 
generar el archivo .war. Una vez generado y siempre y cuando se tenga la variable 
de entorno "CATALINA_HOME" con la ruta del Tomcat 7 debidamente seteada, ejecutar
el comando "mvn cargo:deploy". Si no se tiene la variable, copiar el archivo 
"RCVFrontEnd.war" dentro de la carpeta "webapps" del Tomcat 7. 

La instalación de el modulo RCVFrontEnd se debe realizar en el mismo servidor donde
esta el modulo RCVBackEnd.

Una vez instalados los módulos RCVFrontEnd y RCVBackEnd y con el servidor iniciado,
en el navegador se escribe la URL: "http://<ip servidor>:<puerto Tomcar>/RCVFrontEnd/".

La aplicación web cuenta con una barra de navegación, un panel donde se encuentra el 
mapa o superficie de desplazamiento y en la parte inferior una consola la cual permite 
escribir diferentes comandos. Los comando validos son:

	* Locate	retorna la ubicación del vehículo y muestra en rojo la celda donde 
				esta ubicado.
	* Map 		retorna las dimensiones del mapa.
	* Map n,m 	permite modificar las dimensiones del map enviando un n y m validos.
	* Restart 	ubica el vehículo en la posición 0,0.

De igual forma en la consola es posible ejecutar los comandos de movimiento sin
necesidad de una palabra clave, es decir escribiendo solo el comando deseado ej: 10,N
o una serie de comandos de movimientos separados por punto y coma (;). Esta consola 
consta de un area donde se muestran los comandos ejecutados con el prefijo "COMANDO",
también se muestran los mensajes enviados desde el servidor en caso que no se puede
ejecutar el comando, con un prefijo "ERROR". Cuando se ejecutan los comandos "Locate"
y "Map" se muestra la posición de vehículo con un prefijo "VEHICULO" y las dimensiones
del mapa con el prefijo "MAP". 

En la parte superior de la pantalla se encuentran tres botones Mapa, Reiniciar Posición
y Ubicar Vehículo. El botón Mapa permite crear el mapa de una forma mas intuitiva 
arrastrando el cursor por el panel y seleccionando el tamaño del mapa. Por cuestiones
de performance esta forma de generar el mapa solo permite crearlo con un máximo de 
veinte (20) filas y veinte (20) columnas, sin embargo ejecutando el comando Map n,m 
se puede crear un mapa mucho mas grande.