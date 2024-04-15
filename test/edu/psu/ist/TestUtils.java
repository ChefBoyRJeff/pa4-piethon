package edu.psu.ist;

import antlr4.edu.psu.ist.parser.PiethonParser;
import edu.psu.ist.analyzer.PieAnalyzer;
import edu.psu.ist.analyzer.PieErrorMessage;
import edu.psu.ist.analyzer.utils.Options;
import edu.psu.ist.analyzer.utils.Result;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public abstract class TestUtils {

    public final Result<PiethonParser.ScriptContext, List<PieErrorMessage>> check(String s) {
        return new PieAnalyzer().setOptions(Options.TestOpts).setScriptCode(
                "<test>", s).check();
    }

    public <T extends PieErrorMessage> void expectError(
            Class<T> expectedErrorClass,
            Result<PiethonParser.ScriptContext, List<PieErrorMessage>> result,
            int howManyOfExpectedErrorType) {
        if (result.isOk()) {
            Assertions.fail("Expected failure, but got success.");
        }
        else {
            List<? extends PieErrorMessage> errors = result.getError();
            boolean foundExpectedError = false;
            int actualErrorCount = 0;
            for (var err : errors) {
                if (expectedErrorClass.isAssignableFrom(err.getClass())) {
                    foundExpectedError = true;
                    actualErrorCount++;
                }
            }

            String actualErrors = String.join(", ",
                    errors.stream().map(error -> error.getClass().getSimpleName()).toList());
            if (!foundExpectedError) {
                Assertions.fail("Expected an error of type " +
                        expectedErrorClass.getSimpleName() + ", but found: " + actualErrors);
            }
            if (actualErrorCount != howManyOfExpectedErrorType) {
                Assertions.fail(String.format("Expected %s errors of type %s,"
                        + " " + "but found", howManyOfExpectedErrorType,
                        actualErrorCount));
            }
        }
    }

}
