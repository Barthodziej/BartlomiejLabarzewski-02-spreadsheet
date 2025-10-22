package uj.wmii.pwj.spreadsheet.expressions;

import uj.wmii.pwj.spreadsheet.operations.OperationCommand;

public class BinaryOperationExpression implements IBinaryOperationExpression {

    private final IExpression left;
    private final IExpression right;
    private final OperationCommand operation;
    private int value;
    private boolean isEvaluated;

    public BinaryOperationExpression(IExpression leftArg, IExpression rightArg, OperationCommand operationArg) {
        left = leftArg;
        right = rightArg;
        operation = operationArg;
    }

    @Override
    public boolean canBeEvaluated() {
        return left.canBeEvaluated() && right.canBeEvaluated();
    }

    @Override
    public boolean isEvaluated() {
        return isEvaluated;
    }

    @Override
    public void evaluate() {
        left.evaluate();
        right.evaluate();
        operation.setArgument(0, left.getValue());
        operation.setArgument(1, right.getValue());
        value = operation.execute();
        isEvaluated = true;
    }

    @Override
    public int getValue() {
        return value;
    }
}
