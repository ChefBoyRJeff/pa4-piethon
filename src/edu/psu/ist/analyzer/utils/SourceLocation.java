package edu.psu.ist.analyzer.utils;

/**
 * A class that represents the physical source location of some parsed
 * syntactic entity.
 *
 * @param source       the parser input text
 * @param locationKind the source location kind (real or synthetic)
 * @param beginLine    the line number where the entity begins.
 * @param beginCol     the column number where the entity begins.
 * @param endLine      the line number where the entity ends.
 * @param endCol       the column number where the entity ends.
 */
public record SourceLocation(TextInput source,
                             int beginLine, int beginCol,
                             int endLine, int endCol) {

    @Override public String toString() {
        return String.format("%s:%s:%s", source.name(), beginLine, beginCol);
    }
}
