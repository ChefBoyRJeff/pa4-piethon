package edu.psu.ist.analyzer;

/** All (primitive) types currently supported by Piethon. */
public enum PieType {
    /** Piethon's baked-in <b>Int32</b> type (a 32-bit integer type). */
    Int32,

    /** Piethon's baked-in <b>Boolean</b> type. */
    Bool,

    /** Piethon's baked-in <b>Void</b> type. */
    Void,

    /**
     * Used to denote an erroneous (unknown) type. This type should only appear
     * in the event of a malformed (semantically incorrect) script.
     * <p>
     * So when an expression is not type-correct, the {@link PieScriptCheckingListener}
     * should map that expression to this distinguished error type.
     */
    Error
}
