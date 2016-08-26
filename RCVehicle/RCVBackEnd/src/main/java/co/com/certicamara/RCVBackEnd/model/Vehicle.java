package co.com.certicamara.RCVBackEnd.model;

import java.awt.Point;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import co.com.certicamara.RCVBackEnd.exceptions.BusinessException;

@Repository
public class Vehicle {
	
	@Value("${OUT_OF_LIMITS_MESSAGE}")
	private String outOfLimitsMessage;
	
	@Autowired
	private Map map;
	
	private Point position;
	
	public Vehicle () {
		position = new Point(0, 0);
	}
	
	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}
	
	public Point moveVehicle (Point newPosition) throws BusinessException{
		if (map.isValidPosition(newPosition)){
			this.position = newPosition;
			return this.position;
		}
		else
			throw new BusinessException(outOfLimitsMessage);
	}

}
