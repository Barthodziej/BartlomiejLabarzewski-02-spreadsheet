package uj.wmii.pwj.spreadsheet.cellmatrix;

import uj.wmii.pwj.spreadsheet.ICell;

public interface ICellMatrix {
    ICell getCell(String address);
    void setCell(String address, ICell content);
    String[][] toStringMatrix();
}
