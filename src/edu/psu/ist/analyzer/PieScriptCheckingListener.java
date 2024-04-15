package edu.psu.ist.analyzer;

import antlr4.edu.psu.ist.parser.PiethonBaseListener;
import antlr4.edu.psu.ist.parser.PiethonParser;
import edu.psu.ist.analyzer.entry.SymbolTableEntry;
import edu.psu.ist.analyzer.utils.Result;
import edu.psu.ist.analyzer.utils.TextInput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PieScriptCheckingListener extends PiethonBaseListener {
    /**
     * A special map that keeps track of all global procedures seen so far in
     * the script.
     */
    private final Map<String, SymbolTableEntry.ProcDefEntry> procedures =
            new HashMap<>();

    /**
     * A mutable field that will get reassigned each time we leave the local
     * scope of a procedure definition.
     * <p>
     * Invariant: this will map strings (names) to either local symbol
     * definitions: either formal parameters or variables -- never procedures.
     */
    private Map<String, SymbolTableEntry> currLocalScope;

    /**
     * A list that accumulates any errors found while traversing the tree for
     * this script.
     */
    private final List<PieErrorMessage> errors = new ArrayList<>();

    /**
     * The name of the script and its original source text
     * (needed when constructing {@link PieErrorMessage}s)
     */
    private final TextInput source;

    /**
     * The top level antlr4-generated script context node. You shouldn't need to
     * do anything with this field; it's just returned if the tree is valid in
     * the {@link #getCheckedScript()} method.
     */
    private final PiethonParser.ScriptContext hostContext;

    // todo add a field for exp tpe checking

    public PieScriptCheckingListener(TextInput source,
                                     PiethonParser.ScriptContext hostContext) {
        this.source = source;
        this.hostContext = hostContext;
    }

    /** This method should only be called once the treewalk has completed. */
    public Result<PiethonParser.ScriptContext, List<PieErrorMessage>> getCheckedScript() {
        if (errors.isEmpty()) {
            // script is semantically valid, return a ref to the root of the
            // script.
            return Result.ok(hostContext);
        }
        else {
            // else return any errors observed during the walk
            return Result.err(errors);
        }
    }

    // todo todo
}
