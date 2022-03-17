package soulCode.enterprise.services.exceptions;

public class DataEntegrityViolationException extends RuntimeException {
	
	//sinaliza a versão da aplicação
	private static final long serialVersionUID = 1L;
	
	public DataEntegrityViolationException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataEntegrityViolationException(String message) {
		super(message);
	}	
}
