package br.com.edu.driver.exception;

@SuppressWarnings("serial")
public class PreconditionException extends Throwable {
	public PreconditionException() {
		super();
	}
	
	public PreconditionException(Throwable cause){
		super(cause);
	}
	
	public PreconditionException(String message) {
		super(message);
	}
	
	public PreconditionException(String message, Throwable cause) {
		super(message, cause);
	}
}
