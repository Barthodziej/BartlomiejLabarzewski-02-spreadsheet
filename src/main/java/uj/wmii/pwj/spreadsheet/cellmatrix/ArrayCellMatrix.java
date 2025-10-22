package uj.wmii.pwj.spreadsheet.cellmatrix;

import uj.wmii.pwj.spreadsheet.ICell;
import uj.wmii.pwj.spreadsheet.Cell;
import uj.wmii.pwj.spreadsheet.ICellAddressParser;
import uj.wmii.pwj.spreadsheet.expressions.IExpression;
import uj.wmii.pwj.spreadsheet.expressions.IExpressionParser;

public class ArrayCellMatrix implements ICellMatrix {
    private final ICell[][] cells;
    private final IExpressionParser expressionParser;
    private final ICellAddressParser addressParser;

    public ArrayCellMatrix(int rowsNum, int colsNum, IExpressionParser expressionParserArg, ICellAddressParser addressParserArg) {
        this.cells = new ICell[rowsNum][colsNum];
        this.expressionParser = expressionParserArg;
        expressionParser.injectMatrix(this);
        this.addressParser = addressParserArg;
        for (int row = 0; row < rowsNum; row++) {
            for (int col = 0; col < colsNum; col++) {
                cells[row][col] = new Cell();
            }
        }
    }

    public ArrayCellMatrix(String[][] input, IExpressionParser expressionParserArg, ICellAddressParser addressParserArg) {
        this(input.length, input[0].length, expressionParserArg, addressParserArg);
        for (int row = 0; row < input.length; row++) {
            for (int col = 0; col < input[0].length; col++) {
                IExpression expression = expressionParser.parse(input[row][col]);
                cells[row][col].setExpression(expression);
                String[] dependencies = expressionParser.dependencies(input[row][col]);
                for (String dependency : dependencies) {
                    getCell(dependency).getUpdateManager().subscribe(cells[row][col]);
                }
            }
        }
        for (int row = 0; row <  input.length; row++) {
            for (int col = 0; col <  input[0].length; col++) {;
                cells[row][col].update();
            }
        }
    }

    @Override
    public ICell getCell(String cellAddress) {
        // TODO: Exceptions when the argument is:
        //       - null
        //       - an invalid string to be a signature
        int[] coordinates = addressParser.parse(cellAddress);
        return cells[coordinates[0]][coordinates[1]];
    }

    @Override
    public void setCell(String cellAddress, ICell content) {
        int[] coordinates = addressParser.parse(cellAddress);
        cells[coordinates[0]][coordinates[1]] = content;
    }

    @Override
    public String[][] toStringMatrix() {
        String[][] result = new String[cells.length][cells[0].length];
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[0].length; col++) {
                result[row][col] = String.valueOf(cells[row][col].getExpression().getValue());
            }
        }
        return result;
    }
}
