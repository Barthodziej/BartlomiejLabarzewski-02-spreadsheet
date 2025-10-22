package uj.wmii.pwj.spreadsheet.operations;

public class OperationCommandCreator {
    public OperationCommand create(String commandString) {
        return switch (commandString) {
            case "ADD" -> new AddCommand();
            case "SUB" -> new SubCommand();
            case "MUL" -> new MulCommand();
            case "DIV" -> new DivCommand();
            case "MOD" -> new ModCommand();
            default -> null;
        };
    }
}
