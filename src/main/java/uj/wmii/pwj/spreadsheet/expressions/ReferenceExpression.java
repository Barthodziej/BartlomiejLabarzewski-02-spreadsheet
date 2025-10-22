package uj.wmii.pwj.spreadsheet.expressions;

import uj.wmii.pwj.spreadsheet.cellmatrix.ICellMatrix;

public class ReferenceExpression implements IReferenceExpression {

    private final ICellMatrix matrix;
    private final String address;
    private int value;
    private boolean isEvaluated;

    public ReferenceExpression(ICellMatrix matrix, String address) {
        this.matrix = matrix;
        this.address = address;
    }

    @Override
    public boolean canBeEvaluated() {
        return matrix.getCell(address).getExpression().isEvaluated();
    }

    @Override
    public boolean isEvaluated() {
        return isEvaluated;
    }

    @Override
    public void evaluate() {
        value = matrix.getCell(address).getExpression().getValue();
        isEvaluated = true;
    }

    @Override
    public int getValue() {
        return value;
    }
}
