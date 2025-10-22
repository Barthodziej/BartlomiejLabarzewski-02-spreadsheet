package uj.wmii.pwj.spreadsheet;

import uj.wmii.pwj.spreadsheet.cellmatrix.ArrayCellMatrix;
import uj.wmii.pwj.spreadsheet.cellmatrix.ICellMatrix;
import uj.wmii.pwj.spreadsheet.expressions.SimpleExpressionParser;

public class Spreadsheet {
    public String[][] calculate(String[][] input) {
        ICellMatrix cells = new ArrayCellMatrix(input, new SimpleExpressionParser(), new ExcelCellAddressParser());
        return cells.toStringMatrix();
    }
}
