package edu.psu.ist.analyzer;

import edu.psu.ist.analyzer.utils.Digraph;

/**
 * A basic node type to represent a procedure. In this project, this will use
 * the vertex type when using your {@link Digraph} in the {@link PieGraphBuildingListener} class.
 * <p>
 * Add any additional fields/components to this record class if you need
 * additional info when generating the call graph PNG in {@link PieAnalyzer#exportGraph(Digraph, String, String)}
 * to further customize the call graph you generate.
 *
 * @param name the name of the procedure.
 */
public record ProcNode(String name){

    @Override public String toString() {
        return String.format("%s(..)", name);
    }
}
