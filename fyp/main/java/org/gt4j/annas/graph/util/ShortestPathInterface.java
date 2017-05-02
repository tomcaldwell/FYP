package org.gt4j.annas.graph.util;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.misc.Path;

/**
 * Interface for all shortest path algorithms.
 * 
 * @author Sam Wilson
 *
 */
public interface ShortestPathInterface<V, E extends EdgeInterface<V>> extends Algorithm<Path<V, E>> {

}
