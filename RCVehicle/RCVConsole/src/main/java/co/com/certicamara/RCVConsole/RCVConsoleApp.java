package co.com.certicamara.RCVConsole;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import co.com.certicamara.RCVConsole.exceptions.BusinessException;
import co.com.certicamara.RCVConsole.service.RCVehicleService;

public class RCVConsoleApp {
	private final static String BEANS_FILE = "RCVConsole-beans.xml";
	private static ApplicationContext context;

	public static void main(String[] args) {
		context = new ClassPathXmlApplicationContext(BEANS_FILE);

		RCVehicleService rcVehicleService = context.getBean(RCVehicleService.class);

		BufferedReader br = null;

		try {

			br = new BufferedReader(new InputStreamReader(System.in));

			boolean exit = false;
			System.out.println("Para salir digite la letra 'q'");
			while (true) {
				System.out.println("Ingrese las dimensiones de la superficie de desplazamiento (N,M)");
				String input = br.readLine();

				if ("q".equals(input)) {
					System.out.println("Adios!");
					exit = true;
					break;
				}

				if (!input.matches("[0-9]*,[0-9]*")) {
					System.out.println("Las dimensiones no son correctas.");
					System.out.println("Intente nuevamente!");
				} else {
					String[] dimensions = input.split(",");
					int n = Integer.parseInt(dimensions[0]);
					int m = Integer.parseInt(dimensions[1]);
					try {
						rcVehicleService.createMap(n, m);
					} catch (BusinessException e) {
						System.out.println(e.toString());
					}
					break;
				}
			}

			while (exit == false && true) {
				System.out.println("Ingrese el comando para mover el vehiculo (ej: 3,N ó 3,N;4,S;)");
				String input = br.readLine();

				if ("q".equals(input)) {
					System.out.println("Adios!");
					break;
				}

				if (!input.matches("([0-9]*,[NSEO]|;)+")) {
					System.out.println("Error en formato de comando");
					System.out.println("Intente nuevamente!");
				} else {
					String[] commands = input.split(";");
					try {
						rcVehicleService.moveVehicle(commands);
					} catch (BusinessException e) {
						System.out.println(e.toString());
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
