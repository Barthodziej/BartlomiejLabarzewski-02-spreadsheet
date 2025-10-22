package uj.wmii.pwj.spreadsheet.expressions;

public interface IExpression {
    // Infinite loop when the dependency graph of cells is cyclic
    boolean canBeEvaluated();
    boolean isEvaluated();
    void evaluate();
    int getValue();
}
