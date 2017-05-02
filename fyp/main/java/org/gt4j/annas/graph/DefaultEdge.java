package org.gt4j.annas.graph;

/**
 * Default edge between vertices of type String
 * 
 * @author Sam Wilson
 */
public class DefaultEdge implements EdgeInterface<String> {

	private String head;

	private String tail;

	/**
	 * Default constructor
	 */
	public DefaultEdge() {
		super();
	}

	@Override
	public String toString() {
		return this.tail + "->" + this.head;
	}

	@Override
	public String getHead() {
		return this.head;
	}

	@Override
	public void setHead(String head) {
		this.head = head;
	}

	@Override
	public String getTail() {
		return this.tail;
	}

	@Override
	public void setTail(String tail) {
		this.tail = tail;
	}

	@Override
	public boolean isIncident(String vertex) {
		return this.head.equals(vertex) || this.tail.equals(vertex);
	}

	@Override
	public String getOtherEndpoint(String vertex) {
		if (this.head.equals(vertex)) {
			return this.tail;
		} else if (this.tail.equals(vertex)) {
			return this.head;
		} else {
			return null;
		}
	}
}
