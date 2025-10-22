package uj.wmii.pwj.spreadsheet;

import uj.wmii.pwj.spreadsheet.expressions.IExpression;

public class Cell implements ICell {

    private final CellUpdateManager updateManager;
    private IExpression expression;

    public Cell() {
        updateManager = new CellUpdateManager();
        expression = null;
    }

    @Override
    public void setExpression(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public IExpression getExpression() {
        return expression;
    }

    @Override
    public CellUpdateManager getUpdateManager() {
        return updateManager;
    }

    @Override
    public void update() {
        if (expression.canBeEvaluated()) {
            expression.evaluate();
            updateManager.notifyListeners();
        }
    }
}
