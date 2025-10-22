package uj.wmii.pwj.spreadsheet.expressions;

import uj.wmii.pwj.spreadsheet.cellmatrix.ICellMatrix;
import uj.wmii.pwj.spreadsheet.operations.OperationCommandCreator;
import uj.wmii.pwj.spreadsheet.operations.OperationCommand;

import java.util.LinkedList;

public class SimpleExpressionParser implements IExpressionParser {

    private ICellMatrix cellMatrix;


    @Override
    public void injectMatrix(ICellMatrix matrixArg) {
        cellMatrix = matrixArg;
    }


    @Override
    public IExpression parse(String input) {
        if (input.matches("-?\\d+")) {
            return new ValueExpression(Integer.parseInt(input));
        }
        if (input.matches("\\$[A-Z]+[1-9][0-9]*")) {
            return new ReferenceExpression(cellMatrix, input.substring(1));
        }
        if (input.matches("=(ADD|SUB|MUL|DIV|MOD)\\((\\$[A-Z]+[1-9][0-9]*|[-+]?\\d+),(\\$[A-Z]+[1-9][0-9]*|[-+]?\\d+)\\)")) {
            String operationString = input.substring(1, 4);
            String firstArgString = input.substring(input.indexOf('(') + 1, input.indexOf(','));
            String secondArgString = input.substring(input.indexOf(',') + 1, input.indexOf(')'));

            IExpression leftArg = parse(firstArgString);
            IExpression rightArg = parse(secondArgString);

            OperationCommandCreator operationCreator = new OperationCommandCreator();
            OperationCommand operation = operationCreator.create(operationString);

            return new BinaryOperationExpression(leftArg, rightArg, operation);

        }
        return null;
    }

    private String firstDependency(String input, int dollarIndex) {
        boolean toContinue = true;
        int i = dollarIndex + 1;
        while (toContinue && i < input.length()) {
            char currentChar = input.charAt(i);
            if (('0' <=  currentChar && currentChar <= '9') || ('A' <= currentChar && currentChar <= 'Z') || ('a' <= currentChar && currentChar <= 'z')) {
                i++;
            }
            else {
                toContinue = false;
            }
        }
        return input.substring(dollarIndex + 1, i);
    }

    @Override
    public String[] dependencies(String input) {
        LinkedList<String> dependencies = new LinkedList<>();
        int currentIndex = 0;
        int dollarIndex;
        while ((dollarIndex = input.indexOf('$', currentIndex)) != -1) {
            dependencies.add(firstDependency(input, dollarIndex));
            currentIndex = dollarIndex + 1;
        }
        return dependencies.toArray(new String[0]);
    }
}
