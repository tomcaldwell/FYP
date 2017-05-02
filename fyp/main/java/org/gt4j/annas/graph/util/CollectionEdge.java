package org.gt4j.annas.graph.util;

import java.util.Collection;

import org.gt4j.annas.graph.EdgeInterface;

public class CollectionEdge<V> implements EdgeInterface<Collection<V>> {

	private Collection<V> head;

	private Collection<V> tail;

	@Override
	public Collection<V> getHead() {
		return head;
	}

	@Override
	public Collection<V> getTail() {
		return tail;
	}

	@Override
	public void setHead(Collection<V> vertex) {
		this.head = vertex;
	}

	@Override
	public void setTail(Collection<V> vertex) {
		this.tail = vertex;
	}

	@Override
	public boolean isIncident(Collection<V> vertex) {
		return this.head.equals(vertex) || this.tail.equals(vertex);
	}

	@Override
	public Collection<V> getOtherEndpoint(Collection<V> vertex) {
		if (this.head.equals(vertex)) {
			return this.tail;
		} else if (this.tail.equals(vertex)) {
			return this.head;
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return "CollectionEdge [head=" + head + ", tail=" + tail + "]";
	}

}