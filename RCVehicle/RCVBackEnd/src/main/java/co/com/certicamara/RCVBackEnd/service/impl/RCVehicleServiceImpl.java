package co.com.certicamara.RCVBackEnd.service.impl;

import java.awt.Point;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.com.certicamara.RCVBackEnd.exceptions.BusinessException;
import co.com.certicamara.RCVBackEnd.model.Map;
import co.com.certicamara.RCVBackEnd.model.Vehicle;
import co.com.certicamara.RCVBackEnd.service.RCVehicleService;

@Service
public class RCVehicleServiceImpl implements RCVehicleService{
	
	@Value("${INVALID_COMMANDS}")
	private String invalidCommnands;

	@Value("${INVALID_MAP_DIMENSIONS}")
	private String invalidMapDimansions;

	@Autowired
	private Map map;

	@Autowired
	private Vehicle vehicle;

	@Override
	public void createMap(int n, int m) throws BusinessException {
		if (n < 0 || m < 0) {
			throw new BusinessException(invalidMapDimansions);
		}
		map.setAxisY(n);
		map.setAxisX(m);
		System.out.println("hola");
	}

	public void moveVehicle(String... commands) throws BusinessException {
		Point newPosition = null;
		System.out.println("hola");
		for (String command : commands) {
			isValidCommand(command);
			newPosition = transformCommandToPoint(vehicle.getPosition(), command);
			newPosition = vehicle.moveVehicle(newPosition);
		}
		System.out.println("hola");
		System.out.println("hola");
		//dsfsdffe
	}

	/**
	 * Valida si un comando es correcto
	 * 
	 * @param command
	 * @throws Exception
	 */
	private void isValidCommand(String command) throws BusinessException {
		System.out.println("hola");
		if (command.isEmpty())
			throw new BusinessException(invalidCommnands);
		if (!command.matches("[0-9]*,[NSOE]"))
			throw new BusinessException(invalidCommnands);
	}

	/**
	 * Transforma el comando en una nueva posicion segun la posicion actual del
	 * vehiculo
	 * 
	 * @param command
	 * @return
	 */
	private Point transformCommandToPoint(Point originalPoint, String command) {
		// Si es N se tiene que mover en el eje Y de forma positiva
		// Si es S se tiene que mover en el eje Y de forma negativa
		// Si es E se tiene que mover en el eje X de forma positiva
		// Si es O se tiene que mover en el eje X de forma negativa
		System.out.println("hola");
		String[] splitCommand = command.split(",");
		int steps = Integer.parseInt(splitCommand[0]);
		String dir = splitCommand[1];
		Point newPoint = new Point(originalPoint);

		if (dir.equals("N"))
			newPoint.y += steps;
		else if (dir.equals("S"))
			newPoint.y -= steps;
		else if (dir.equals("E"))
			newPoint.x += steps;
		else if (dir.equals("O"))
			newPoint.x -= steps;

		System.out.println("hola");
		return newPoint;
	}

	/**
	 * Retorna la posicion actual del vehiculo en formato (fila, columna) Dado
	 * que el eje x son las columnas, el eje y son las filas y se necesita
	 * mostrar el punto en formato fila, columna es necesario invertir los ejes
	 * antes de inviar la posicion
	 */
	public String getPosition() {
		String point = vehicle.getPosition().y + "," + vehicle.getPosition().x;
		return point;
	}

	public Map getMap() {
		return map;
	}

	/**
	 * Reinicia la posicion del vehiculo
	 */
	public void restartVehiclePosition() {
		vehicle.setPosition(new Point(0, 0));
	}

}
