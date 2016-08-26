package co.com.certicamara.RCVBackEnd.exceptions;

public class BusinessException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public BusinessException(String message) {
		super(message);
	}
	
	public String toString() {
		return getMessage();
	}
}
