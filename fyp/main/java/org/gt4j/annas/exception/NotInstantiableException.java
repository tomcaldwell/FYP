package org.gt4j.annas.exception;

public class NotInstantiableException extends AnnasRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7283097866438511682L;

	public NotInstantiableException() {
		super();
	}

	public NotInstantiableException(String message) {
		super(message);
	}

	public NotInstantiableException(Throwable cause) {
		super(cause);
	}

	public NotInstantiableException(String message, Throwable cause) {
		super(message, cause);
	}

}
