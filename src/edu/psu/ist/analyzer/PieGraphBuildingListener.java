package edu.psu.ist.analyzer;

import antlr4.edu.psu.ist.parser.PiethonBaseListener;
import edu.psu.ist.analyzer.utils.Digraph;

public final class PieGraphBuildingListener extends PiethonBaseListener {

    private final Digraph<ProcNode> g;

    public PieGraphBuildingListener() {
        this.g = new Digraph<>();
    }

    /**
     * Retrieves the graph for the script built up over the course of the
     * traversal of this script.
     */
    public Digraph<ProcNode> getGraph() {
        return g;
    }

    // todo -- override the listener methods you need from PiethonBaseListener
}
