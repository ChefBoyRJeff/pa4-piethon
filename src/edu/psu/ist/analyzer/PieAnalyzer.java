package edu.psu.ist.analyzer;

import antlr4.AntlrErrorReportingListener;
import antlr4.edu.psu.ist.parser.PiethonLexer;
import antlr4.edu.psu.ist.parser.PiethonParser;
import edu.psu.ist.analyzer.errors.ParseError;
import edu.psu.ist.analyzer.utils.*;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.List;

public final class PieAnalyzer {

    /** The current {@code .pie} source to be parsed into a CST. */
    private TextInput currentSource;

    /** Stores current settings/options for the checker (minimal currently). */
    private Options options;

    /** Adds the source code with the given {@code name} and {@code text}. */
    public PieAnalyzer setScriptCode(String name, String text) {
        if (name == null || text == null) {
            throw new IllegalArgumentException("name, text should not be null");
        }
        this.currentSource = new TextInput(name, text);

        return this;
    }

    // a sample (throwaway) method .. assume this is in: PieAnalyzer.java
    public PieAnalyzer testSampleListener() {
        var testSourceCode = new TextInput("test.pie", """
                   def foo(x : Bool) : Int32 is
                      return x + 2;
                   end

                   def moo() : Void is
                   end
                """);
        PiethonParser.ScriptContext scriptRootNode =
                parseRoot(testSourceCode).get();
        SampleListener printingListener = new SampleListener();
        ParseTreeWalker.DEFAULT.walk(printingListener, scriptRootNode);
        return this;
    }

    public PieAnalyzer setOptions(Options o) {
        this.options = o;
        return this;
    }

    /** Removes the current {@code .pie} script with the given {@code name}. */
    public PieAnalyzer removeSourceCode() {
        this.currentSource = null;
        return this;
    }

    /**
     * Returns a {@link Result} instance that is either a {@link Result.Ok}
     * holding the successully parsed syntax tree for the current piethon
     * program or an {@link Result.Err} that encapsulates a list of error
     * messages.
     */
    public Result<PiethonParser.ScriptContext, List<PieErrorMessage>> check() {

        if (currentSource == null) {
            throw new IllegalStateException("Cannot call check until a " +
                    "script is set (call setScriptCode(..))");
        }
        var parseResult = parseRoot(currentSource);
        if (parseResult.isError()) {
            if (!options.runSilent()) {
                reportErrors(parseResult.getError());
            }
            return parseResult;
        }

        // this next line shouldn't fail (we would've already returned in the
        // if-stmt above)
        PiethonParser.ScriptContext scriptRootNode = parseResult.get();

        // ok to continue... now do the semantic checks specified

        PieScriptCheckingListener checkingListener =
                new PieScriptCheckingListener(currentSource, scriptRootNode);
        // walk the parse tree
        ParseTreeWalker.DEFAULT.walk(checkingListener, scriptRootNode);

        // returns a Result containing either the root of the sucessfully
        // checked tree or a list of PieErrorMessages.
        var result = checkingListener.getCheckedScript();
        if (result.isError() && !options.runSilent()) {
            // print out any semantic errors
            reportErrors(result.getError());
        }
        return result;
    }

    private void reportErrors(List<PieErrorMessage> errors) {
        for (var err : errors) {
            System.err.println(err);
        }
    }

    public Digraph<ProcNode> buildGraph() {

        var checkResult = check(); // will throw an exception if script not set
        if (!checkResult.isOk()) {
            // report any errors encountered to the console
            throw new IllegalArgumentException("Script contains errors " +
                    "(call check first to ensure the script is well formed)");
        }
        PieGraphBuildingListener l = new PieGraphBuildingListener();
        // walk the tree & build the graph
        ParseTreeWalker.DEFAULT.walk(l, checkResult.get());
        return l.getGraph();
    }

    /**
     * Given a call graph {@code g}, exports a png visualizing the graph to
     * the project root directory.
     * <p>
     * Implement this method using
     * <a href="https://github.com/nidi3/graphviz-java">this graphviz library</a>
     */
    public void exportGraph(Digraph<ProcNode> g, String outputImageName,
                            String graphTitle) {

        // todo todo
    }

    /**
     * Given a piethon {@code source}, returns an {@link Result} instance that
     * holds either the root of a successfully parsed piethon parse tree, or a
     * List of {@link ParseError} messages.
     */
    private Result<PiethonParser.ScriptContext, List<PieErrorMessage>> parseRoot(TextInput source) {
        var errorListener = new AntlrErrorReportingListener(source);
        var lexer = new PiethonLexer(CharStreams.fromString(source.text(),
                source.name()));

        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);

        var parser = new PiethonParser(new CommonTokenStream(lexer));
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);

        PiethonParser.ScriptContext tree = parser.script();
        // NOTE: we don't want our parser to stop cold on the first
        // syntactic error encountered
        if (!errorListener.errors().isEmpty()) {
            // failure (one or more syntactic errors)
            return Result.err(errorListener.errors());
        }
        return Result.ok(tree);
    }
}
