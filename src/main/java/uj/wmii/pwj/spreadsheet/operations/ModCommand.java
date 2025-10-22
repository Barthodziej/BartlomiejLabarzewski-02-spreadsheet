package uj.wmii.pwj.spreadsheet.operations;

public class ModCommand implements OperationCommand {

    int[] args;

    public ModCommand() {
        args = new int[2];
    }

    @Override
    public void setArgument(int index, int value) {
        args[index] = value;
    }

    @Override
    public int execute() {
        return args[0] % args[1];
    }
}
