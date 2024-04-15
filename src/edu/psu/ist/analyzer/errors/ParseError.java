package edu.psu.ist.analyzer.errors;

import edu.psu.ist.analyzer.PieErrorMessage;
import edu.psu.ist.analyzer.utils.SourceLocation;

public record ParseError(String msg, int line, int col, SourceLocation loc)
        implements PieErrorMessage {

    @Override public String kind() {
        return "Parse Error";
    }

    @Override public String message() {
        return String.format(">> Parse Error (%s) - %s", loc, msg);
    }
}
