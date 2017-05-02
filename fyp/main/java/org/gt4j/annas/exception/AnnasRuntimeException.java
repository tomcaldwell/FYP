package org.gt4j.annas.exception;

/**
 * All exceptions thrown by this library extend this class, with the exception of {@link NullPointerException}.
 * @author Sam Wilson
 *
 */
public abstract class AnnasRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1003027789824651416L;

	public AnnasRuntimeException() {
		super();
	}

	public AnnasRuntimeException(String message) {
		super(message);
	}

	public AnnasRuntimeException(Throwable cause) {
		super(cause);
	}

	public AnnasRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

}
