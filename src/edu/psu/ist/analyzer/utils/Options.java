package edu.psu.ist.analyzer.utils;

// overkill, but if you ever want to add additional options to the pie analyzer, do so here.
public record Options(boolean runSilent) {

    /**
     * This is primarily for testing purposes (don't want the output pane to
     * be filled with prints when running jUnit tests)
     */
    public static final Options TestOpts = new Options(true);
}
