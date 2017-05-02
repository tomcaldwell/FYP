package org.gt4j.annas.exception;

public class UnmodifiableGraphException extends AnnasRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1236423827419883808L;

	public UnmodifiableGraphException() {
		super();
	}

	/**
	 * @param message Message
	 * @param cause cause of the exception
	 */
	public UnmodifiableGraphException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message Message to display
	 */
	public UnmodifiableGraphException(String message) {
		super(message);
	}

	/**
	 * @param cause cause of the exception
	 */
	public UnmodifiableGraphException(Throwable cause) {
		super(cause);
	}
}
