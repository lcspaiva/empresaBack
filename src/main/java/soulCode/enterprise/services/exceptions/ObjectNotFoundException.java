package soulCode.enterprise.services.exceptions;

public class ObjectNotFoundException extends RuntimeException{
	//sinaliza a versão da aplicação
	private static final long serialVersionUID = 1L;
	
	//métodos gerados em função da interface
	public ObjectNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ObjectNotFoundException(String message) {
		super(message);
	}	
}
