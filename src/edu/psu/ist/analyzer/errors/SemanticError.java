package edu.psu.ist.analyzer.errors;

import edu.psu.ist.analyzer.PieErrorMessage;
import edu.psu.ist.analyzer.PieType;
import edu.psu.ist.analyzer.SymbolKind;
import edu.psu.ist.analyzer.utils.SourceLocation;

public sealed interface SemanticError extends PieErrorMessage {

    @Override default String kind() {
        return "Semantic error";
    }

    record DupSymbol(String dupName, SymbolKind k,
                     SourceLocation loc) implements SemanticError {
        @Override public String message() {
            return String.format(">> Duplicate %s symbol (%s) - %s", k, loc,
                    dupName);
        }
    }

    // when a referenced symbol is not defined (or it's out of scope)
    record NoSuchSymbol(String referencedSymbol,
                        SourceLocation loc) implements SemanticError {
        @Override public String message() {
            return String.format(">> No such symbol (%s) - %s", loc,
                    referencedSymbol);
        }
    }

    // e.g., use for type errors (e.g., var r : Bool := 1 + 1;)
    record TypeMismatch(PieType expected, PieType actual,
                        SourceLocation loc) implements SemanticError {
        @Override public String message() {
            return String.format(">> Type mismatch (%s) - expected: %s, but " +
                    "got: %s", loc, expected, actual);
        }
    }

    // e.g., calling f(x, y) when function f only takes 1 formal parameter
    record ArgCountMismatch(int numArgsExpected, int numProvided,
                            SourceLocation loc) implements SemanticError {
        @Override public String message() {
            return String.format(">> Operation call too few args (%s) - " +
                    "expected: %s, but got: %s", loc, numArgsExpected,
                    numProvided);
        }
    }

    // either too few or too many return statements
    record TooFewOrTooManyReturns(int numOfReturnsExpected, SourceLocation loc)
            implements SemanticError {
        @Override public String message() {
            return String.format(">> Too many or too few return statements " +
                    "(%s) - expected: %s return statement", loc, numOfReturnsExpected);
        }
    }
}
