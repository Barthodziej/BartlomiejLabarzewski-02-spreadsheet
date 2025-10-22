package uj.wmii.pwj.spreadsheet.operations;

public interface OperationCommand {
    void setArgument(int index, int value);
    int execute();
}
