package uj.wmii.pwj.spreadsheet.operations;

public class SubCommand implements OperationCommand {

    int[] args;

    public SubCommand() {
        this.args = new int[2];
    }

    @Override
    public void setArgument(int index, int value) {
        this.args[index] = value;
    }

    @Override
    public int execute() {
        return args[0] - args[1];
    }
}
