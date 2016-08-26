package co.com.certicamara.RCVConsole.model;

import java.awt.Point;

import org.springframework.stereotype.Repository;

@Repository
public class Map {
	
	private int axisY;
	
	private int axisX;
	
	public Map(){
		this.axisX = 0;
		this.axisY = 0;
	}

	public int getAxisY() {
		return axisY;
	}

	public void setAxisY(int axisY) {
		this.axisY = axisY;
	}

	public int getAxisX() {
		return axisX;
	}

	public void setAxisX(int axisX) {
		this.axisX = axisX;
	}
	
	//Determina si una posicion es validad de acuerdo a sus ejes
	public boolean isValidPosition(Point position){
		if (position.x >= this.axisX)
			return false;
		if (position.y >= this.axisY)
			return false;
		if (position.x < 0)
			return false;
		if (position.y < 0)
			return false;
		return true;
	}
}
