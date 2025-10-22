package uj.wmii.pwj.spreadsheet;

import uj.wmii.pwj.spreadsheet.expressions.IExpression;

public interface ICell extends ICellUpdateListener {
    void setExpression(IExpression expression);
    IExpression getExpression();
    CellUpdateManager getUpdateManager();
    public void update();
}
