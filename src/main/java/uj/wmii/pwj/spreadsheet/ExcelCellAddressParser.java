package uj.wmii.pwj.spreadsheet;

public class ExcelCellAddressParser implements ICellAddressParser {

    private static final int latinAlphabetLength = 26;

    @Override
    public int[] parse(String signature) {
        int[] coordinates = new int[2];
        boolean columnFinished;
        for (Character c : signature.toCharArray()) {
            if ('A' <= c && c <= 'Z' || 'a' <= c && c <= 'z') {
                c = Character.toLowerCase(c);
                coordinates[1] *= latinAlphabetLength;
                coordinates[1] += c - 'a' + 1;
            }
            else if ('0' <= c && c <= '9') {
                columnFinished = true;
                coordinates[0] *= 10;
                coordinates[0] += c - '0';
            }
        }
        coordinates[0]--;
        coordinates[1]--;
        return coordinates;
    }
}
