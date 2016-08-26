package co.com.certicamara.RCVConsole;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.com.certicamara.RCVConsole.exceptions.BusinessException;
import co.com.certicamara.RCVConsole.model.Map;
import co.com.certicamara.RCVConsole.service.RCVehicleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:RCVConsole-beans.xml")
public class RCVehicleConsoleImplTest {

	@Autowired
	private RCVehicleService rcVehicleService;
	
	@Value("${INVALID_COMMANDS}")
	private String invalidCommnands;
	
	@Value("${INVALID_MAP_DIMENSIONS}")
	private String invalidMapDimansions;
	
	@Value("${OUT_OF_LIMITS_MESSAGE}")
	private String outOfLimitsMessage;

	@Test
	public void createMapTest() {
		int n = 1000;
		int m = 5000;

		try {
			rcVehicleService.createMap(0, 0);
			Map map = rcVehicleService.getMap();

			assertEquals(map.getAxisX(), 0);
			assertEquals(map.getAxisY(), 0);

			rcVehicleService.createMap(n, m);

			map = rcVehicleService.getMap();

			assertEquals(m, map.getAxisX());
			assertEquals(n, map.getAxisY());
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void createMapWithNegativeTest() {
		int n = -1000;
		int m = -5000;

		try {
			rcVehicleService.createMap(0, 0);
			Map map = rcVehicleService.getMap();

			assertEquals(map.getAxisX(), 0);
			assertEquals(map.getAxisY(), 0);

			rcVehicleService.createMap(m, n);
		} catch (BusinessException e) {
			assertEquals(invalidMapDimansions, e.getMessage());
		}
	}
	
	@Test
	public void executeNorthCommand(){
		int n = 4;
		int m = 3;
		
		String expectedPoint = "3,0";
		
		String northCommand = "3,N";
		try {
			rcVehicleService.createMap(0, 0);
			Map map = rcVehicleService.getMap();
			assertEquals(map.getAxisX(), 0);
			assertEquals(map.getAxisY(), 0);
			rcVehicleService.createMap(n, m);
			assertEquals(m, map.getAxisX());
			assertEquals(n, map.getAxisY());
			
			rcVehicleService.restartVehiclePosition();
			
			rcVehicleService.moveVehicle(northCommand);
			
			assertEquals(expectedPoint, rcVehicleService.getPosition());
			
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void executeNorthCommandOutOfMap(){
		int n = 4;
		int m = 3;
		
		String northCommand = "5,N";
		try {
			rcVehicleService.createMap(0, 0);
			Map map = rcVehicleService.getMap();
			assertEquals(map.getAxisX(), 0);
			assertEquals(map.getAxisY(), 0);
			rcVehicleService.createMap(n, m);
			assertEquals(m, map.getAxisX());
			assertEquals(n, map.getAxisY());
			
			rcVehicleService.restartVehiclePosition();
			
			rcVehicleService.moveVehicle(northCommand);
			
		} catch (BusinessException e) {
			assertEquals(outOfLimitsMessage, e.getMessage());
		}
	}
	
	@Test
	public void executeArrayOfCommands(){
		int n = 4;
		int m = 3;
		
		String expectedPoint = "3,2";
		String[] Commands = {"2,N", "2,E", "1,S", "2,N", "1,O", "1,E"};
		try {
			rcVehicleService.createMap(0, 0);
			Map map = rcVehicleService.getMap();
			assertEquals(map.getAxisX(), 0);
			assertEquals(map.getAxisY(), 0);
			rcVehicleService.createMap(n, m);
			assertEquals(m, map.getAxisX());
			assertEquals(n, map.getAxisY());
			
			rcVehicleService.restartVehiclePosition();
			
			rcVehicleService.moveVehicle(Commands);
			
			assertEquals(expectedPoint, rcVehicleService.getPosition());
			
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void executeInvestedCommand(){
		int n = 4;
		int m = 3;
		
		String northCommand = "N,5";
		try {
			rcVehicleService.createMap(0, 0);
			Map map = rcVehicleService.getMap();
			assertEquals(map.getAxisX(), 0);
			assertEquals(map.getAxisY(), 0);
			rcVehicleService.createMap(n, m);
			assertEquals(m, map.getAxisX());
			assertEquals(n, map.getAxisY());
			
			rcVehicleService.restartVehiclePosition();
			
			rcVehicleService.moveVehicle(northCommand);
			
		} catch (BusinessException e) {
			assertEquals(invalidCommnands, e.getMessage());
		}
	}
	
	@Test
	public void executeWithoutComaCommand(){
		int n = 4;
		int m = 3;
		
		String northCommand = "5N";
		try {
			rcVehicleService.createMap(0, 0);
			Map map = rcVehicleService.getMap();
			assertEquals(map.getAxisX(), 0);
			assertEquals(map.getAxisY(), 0);
			rcVehicleService.createMap(n, m);
			assertEquals(m, map.getAxisX());
			assertEquals(n, map.getAxisY());
			
			rcVehicleService.restartVehiclePosition();
			
			rcVehicleService.moveVehicle(northCommand);
			
		} catch (BusinessException e) {
			assertEquals(invalidCommnands, e.getMessage());
		}
	}
	
	@Test
	public void executeInvalidDirectionCommand(){
		int n = 4;
		int m = 3;
		
		String northCommand = "5,F";
		try {
			rcVehicleService.createMap(0, 0);
			Map map = rcVehicleService.getMap();
			assertEquals(map.getAxisX(), 0);
			assertEquals(map.getAxisY(), 0);
			rcVehicleService.createMap(n, m);
			assertEquals(m, map.getAxisX());
			assertEquals(n, map.getAxisY());
			
			rcVehicleService.restartVehiclePosition();
			
			rcVehicleService.moveVehicle(northCommand);
			
		} catch (BusinessException e) {
			assertEquals(invalidCommnands, e.getMessage());
		}
	}
	
	@Test
	public void executeInvalidArrayOfCommand(){
		int n = 4;
		int m = 3;
		
		String expectedPoint = "3,2";
		String[] Commands = {"2,N", "2,E", "1,S", "2,N", "1O", "1,O"};
		try {
			rcVehicleService.createMap(0, 0);
			Map map = rcVehicleService.getMap();
			assertEquals(map.getAxisX(), 0);
			assertEquals(map.getAxisY(), 0);
			rcVehicleService.createMap(n, m);
			assertEquals(m, map.getAxisX());
			assertEquals(n, map.getAxisY());
			
			rcVehicleService.restartVehiclePosition();
			
			rcVehicleService.moveVehicle(Commands);
			
		} catch (BusinessException e) {
			assertEquals(invalidCommnands, e.getMessage());
			assertEquals(expectedPoint, rcVehicleService.getPosition());
		}
	}
	
	@Test
	public void executeArrayOfCommandOutOfMap(){
		int n = 4;
		int m = 3;
		
		String expectedPoint = "3,1";
		String[] Commands = {"2,N", "2,E", "1,S", "2,N", "1,O", "2,N", "1,S"};
		try {
			rcVehicleService.createMap(0, 0);
			Map map = rcVehicleService.getMap();
			assertEquals(map.getAxisX(), 0);
			assertEquals(map.getAxisY(), 0);
			rcVehicleService.createMap(n, m);
			assertEquals(m, map.getAxisX());
			assertEquals(n, map.getAxisY());
			
			rcVehicleService.restartVehiclePosition();
			
			rcVehicleService.moveVehicle(Commands);
			
		} catch (BusinessException e) {
			assertEquals(outOfLimitsMessage, e.getMessage());
			assertEquals(expectedPoint, rcVehicleService.getPosition());
		}
	}
}
