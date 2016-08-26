package co.com.certicamara.RCVConsole.service;

import co.com.certicamara.RCVConsole.exceptions.BusinessException;
import co.com.certicamara.RCVConsole.model.Map;

public interface RCVehicleService {
	
	public void createMap(int n, int m) throws BusinessException;
	
	/**
	 * Move el vehiculo dado uno o varios comandos
	 * Si se envian varios comando y uno de ellos es invalido, se ejecuta el movimiento hasta que encuentra el invalido, el resto no los ejecuta
	 * @param commands
	 * @throws BusinessException
	 */
	public void moveVehicle(String... commands) throws BusinessException;
	
	public String getPosition();
	
	public Map getMap();
	
	public void restartVehiclePosition();

}
