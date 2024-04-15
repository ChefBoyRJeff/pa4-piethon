package edu.psu.ist.analyzer.entry;

import edu.psu.ist.analyzer.PieType;
import edu.psu.ist.analyzer.SymbolKind;
import edu.psu.ist.analyzer.utils.SourceLocation;

import java.util.List;

public sealed interface SymbolTableEntry {

    /** Returns the {@link SymbolKind} (tag) for this entry. */
    SymbolKind kind();

    /** Returns the {@link PieType} for this symbol table entry. */
    PieType tpe();

    /** A symbol table type for a global procedure definition. */
    record ProcDefEntry(String name, List<ParamDefEntry> fparams, PieType tpe,
                        SourceLocation loc) implements SymbolTableEntry {

        @Override public SymbolKind kind() {
            return SymbolKind.Procedure;
        }
    }

    /** A symbol table entry type for a local variable definition. */
    record VarDefEntry(String name, PieType tpe,
                       SourceLocation loc) implements SymbolTableEntry {

        @Override public SymbolKind kind() {
            return SymbolKind.Variable;
        }
    }

    /** A symbol table entry for a formal parameter definition. */
    record ParamDefEntry(String name, PieType tpe,
                         SourceLocation loc) implements SymbolTableEntry {
        @Override public SymbolKind kind() {
            return SymbolKind.Parameter;
        }
    }
}
