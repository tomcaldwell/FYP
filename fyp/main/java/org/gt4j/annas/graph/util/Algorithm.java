package org.gt4j.annas.graph.util;

import java.util.concurrent.Callable;

/**
 * Algorithm is the base interface for all algorithms implemented in annas. By
 * implementing this interface it will allow the algorithm to be implemented
 * asynchronously.
 * 
 * @author Sam Wilson
 *
 */
public interface Algorithm<V> extends Callable<V> {

}
