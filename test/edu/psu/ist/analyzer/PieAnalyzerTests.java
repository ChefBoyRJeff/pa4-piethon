package edu.psu.ist.analyzer;

import edu.psu.ist.TestUtils;
import edu.psu.ist.analyzer.errors.SemanticError;
import org.junit.jupiter.api.Test;

// NOTE: these will fail until you implement the logic in PieScriptCheckingListener;
// this file is by no means complete -- just an example of how you can unit test this
// analyzer.
public class PieAnalyzerTests extends TestUtils {

    // testing return stmts:

    @Test public void testBadReturn01() {
        var input = """
                def moo() : Void is
                    return 1; // type mismatch Int32 != Void
                end
                """;

        var result = check(input);
        expectError(SemanticError.TypeMismatch.class, result,
                1);
    }

    @Test public void testBadReturn02() {
        var input = """
                def moo(x : Bool) : Int32 is
                    return x; // <- x has wrong type...
                end
                """;

        var result = check(input);
        expectError(SemanticError.TypeMismatch.class, result,
                1);
    }

    @Test public void testBadReturn03() {
        var input = """
                def moo() : Int32 is
                    return 0;
                    return 0;
                end
                """;

        var result = check(input);
        expectError(SemanticError.TooFewOrTooManyReturns.class, result,
                1);
    }

    // todo: MORE

    // vars

    @Test public void testDupVars() {
        var input = """
                def bar() : Void is
                    var x : Int32 := 0;
                    var x : Bool  := true; // name x is already taken
                    var y : Bool := false;
                    var z : Int32 := 0;
                    var y : Bool := true; // another: y is already taken
                end
                """;

        var result = check(input);
        expectError(SemanticError.DupSymbol.class, result,
                2);
    }

    // todo: many additional things to test (multiple methods,
    //  some involving calls, some involving formal parameters, etc)..

}
