package edu.psu.ist.analyzer;

import edu.psu.ist.analyzer.utils.SourceLocation;

public interface PieErrorMessage {

    /** Returns the kind of message, e.g. "Syntax Error". */
    String kind();

    /** Returns the location where this message originates from. */
    SourceLocation loc();

    /** Returns a short description of the message. */
    String message();
}
