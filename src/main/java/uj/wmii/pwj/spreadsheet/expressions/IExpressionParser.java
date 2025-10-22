package uj.wmii.pwj.spreadsheet.expressions;

import uj.wmii.pwj.spreadsheet.cellmatrix.ICellMatrix;

public interface IExpressionParser {
    void injectMatrix(ICellMatrix matrixArg);
    IExpression parse(String expression);
    String[] dependencies(String expression);
}
