package edu.psu.ist.analyzer.utils;

import java.util.*;

/**
 * An example class for directed graphs. The vertex type can be specified.
 * There are no edge costs/weights.
 *
 * @param <V> the type for vertices.
 */
public final class Digraph<V> {

    /** Stores the vertex and edge data for this graph as an adjacency list. */
    private Map<V, List<V>> neighbors = new HashMap<>();

    /** Adds a vertex to the graph. No-op if the vertex is already present. */
    public void add(V vertex) {
        if (!neighbors.containsKey(vertex)) {
            neighbors.put(vertex, new ArrayList<>());
        }
    }

    public List<V> neighbors(V v) {
        if (!neighbors.containsKey(v)) {
            throw new IllegalArgumentException("vertex: " + v + " not present");
        }
        return neighbors.get(v);
    }

    public Set<V> getVertices() {
        return neighbors.keySet();
    }

    /**
     * Returns {@code true} only if this graph contains {@code v};
     * {@code false} otherwise.
     */
    public boolean contains(V vertex) {
        return neighbors.containsKey(vertex);
    }

    /**
     * Adds an edge ({@code from}, {@code to}) to this graph; if either vertex
     * exists it gets added.
     */
    public void add(V from, V to) {
        this.add(from); // ensure vertex is present first
        neighbors.get(from).add(to);
    }

    public Set<Pair<V, V>> edges() {
        Set<Pair<V, V>> result = new HashSet<>();

        for (V v : neighbors.keySet()) {
            List<V> adjacentVertices = neighbors.get(v);

            for (V adjacentVertex : adjacentVertices) {
                Pair<V, V> edge = new Pair<>(v, adjacentVertex);
                result.add(edge);
            }
        }
        return result;
    }

    /**
     * Removes an edge from this graph. Nothing happens if the edge specified
     * isn't present.
     *
     * @throws IllegalArgumentException if vertices for {@code from} and
     *                                  {@code to} are not present.
     */
    public void remove(V from, V to) {
        if (!contains(from) && contains(to)) {
            throw new IllegalArgumentException("missing vertices in remove");
        }
        neighbors.get(from).remove(to);
    }

    /** Returns the <em>out-degreee</em> of the specified {@code vertex}. */
    public int outDegree(V vertex) {
        return neighbors.getOrDefault(vertex, Collections.emptyList()).size();
    }

    /** Returns the <em>in-degree</em> of the specified {@code vertex}. */
    public int inDegree(V vertex) {
        int result = 0;
        // want to count the number of nodes in the graph that point to: vertex
        for (V v : neighbors.keySet()) {
            // iterate over all vertices
            if (neighbors.get(v) != null && neighbors.get(v).contains(vertex)) {
                result++;
            }
        }
        return result;
    }

    @Override public String toString() {
        var s = new StringBuilder();
        for (V v : neighbors.keySet()) {
            s.append("\n    ")
                    .append(v)
                    .append(" -> ").append(neighbors.get(v));
        }
        return s.toString();
    }
}
