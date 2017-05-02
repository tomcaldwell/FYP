package org.gt4j.annas.exception;

/**
 * 
 * 
 * @author Sam Wilson
 *
 */
public class VertexNotInGraphException extends AnnasRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4393511941344425656L;

	public VertexNotInGraphException() {
	}

	public VertexNotInGraphException(String message) {
		super(message);
	}

	public VertexNotInGraphException(Throwable cause) {
		super(cause);
	}

	public VertexNotInGraphException(String message, Throwable cause) {
		super(message, cause);
	}

}
