package org.gt4j.annas.graph;

/**
 * Default edge between to Integers
 * 
 * @author Sam Wilson
 * 
 */
public class IntegerEdge implements EdgeInterface<Integer> {

	private Integer head;

	private Integer tail;

	/**
	 * Default constructor
	 */
	public IntegerEdge() {
		super();
	}

	@Override
	public String toString() {
		return this.tail + "--" + this.head;
	}

	@Override
	public Integer getHead() {
		return this.head;
	}

	@Override
	public void setHead(Integer head) {
		this.head = head;
	}

	@Override
	public Integer getTail() {
		return this.tail;
	}

	@Override
	public void setTail(Integer tail) {
		this.tail = tail;
	}

	@Override
	public boolean isIncident(Integer vertex) {
		return this.head.equals(vertex) || this.tail.equals(vertex);
	}

	@Override
	public Integer getOtherEndpoint(Integer vertex) {
		if (this.head.equals(vertex)) {
			return this.tail;
		} else if (this.tail.equals(vertex)) {
			return this.head;
		} else {
			return null;
		}
	}

}
