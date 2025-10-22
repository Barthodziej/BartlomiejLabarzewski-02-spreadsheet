package uj.wmii.pwj.spreadsheet.expressions;

public class ValueExpression implements IValueExpression {

    private final int value;

    public ValueExpression(int value) {
        this.value = value;
    }

    @Override
    public boolean canBeEvaluated() {
        return true;
    }

    @Override
    public boolean isEvaluated() {
        return true;
    }

    @Override
    public void evaluate() {}

    @Override
    public int getValue() {
        return value;
    }
}
