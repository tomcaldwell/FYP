package org.gt4j.annas;
import org.gt4j.annas.graph.WeightedEdgeInterface;

public class DefaultWeightedEdge implements WeightedEdgeInterface<String> {

	private String head;

	private String tail;

	private double weight;

	public DefaultWeightedEdge() {
		super();
	}

	@Override
	public String toString() {
		return "Edge [head=" + head + ", tail=" + tail + "]";
	}

	@Override
	public String getHead() {
		return head;
	}

	@Override
	public void setHead(String head) {
		this.head = head;
	}

	@Override
	public String getTail() {
		return tail;
	}

	@Override
	public void setTail(String tail) {
		this.tail = tail;
	}

	@Override
	public double getWeight() {
		return this.weight;
	}

	@Override
	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public boolean isIncident(String vertex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getOtherEndpoint(String vertex) {
		if (this.head.equals(vertex)) {
			return this.tail;
		} else {
			return this.head;
		}
	}

}
