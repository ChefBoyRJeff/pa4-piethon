package antlr4;

import edu.psu.ist.analyzer.PieErrorMessage;
import edu.psu.ist.analyzer.errors.ParseError;
import edu.psu.ist.analyzer.utils.SourceLocation;
import edu.psu.ist.analyzer.utils.TextInput;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import java.util.ArrayList;
import java.util.List;

public class AntlrErrorReportingListener extends BaseErrorListener {

    private final TextInput source;
    private final List<PieErrorMessage> errors = new ArrayList<>();

    public AntlrErrorReportingListener(TextInput source) {
        this.source = source;
    }

    public List<PieErrorMessage> errors() {
        return errors;
    }

    public TextInput source() {
        return source;
    }

    @Override public void syntaxError(Recognizer<?, ?> recognizer,
                                      Object offendingSymbol, int line,
                                      int charPositionInLine, String msg,
                                      RecognitionException e) {

        // nb: + 1 to account for non-zero based starting indices
        charPositionInLine = charPositionInLine + 1;
        String updatedMsg = msg + "(line: " + line + ", column " +
                charPositionInLine + ")";

        ParseError err = new ParseError(updatedMsg, line, charPositionInLine,
                new SourceLocation(source, line, charPositionInLine, line,
                        charPositionInLine));
        errors.add(err);
    }
}
