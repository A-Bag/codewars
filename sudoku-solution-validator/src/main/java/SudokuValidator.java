import java.util.*;

public class SudokuValidator {
    public static boolean check(int[][] sudoku) {
        return checkRowsAndColumns(sudoku) && checkSquares(sudoku);
    }

    public static boolean checkRowsAndColumns(int[][] sudoku){
        for (int i=0; i<9; i++) {
            List<Integer> row = new ArrayList<>();
            List<Integer> column = new ArrayList<>();
            for (int j=0; j<9; j++) {
                row.add(sudoku[i][j]);
                column.add(sudoku[j][i]);
            }
            if (!areAllDigitsIncluded(row) || !areAllDigitsIncluded(column)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkSquares(int[][] sudoku){
        for (int i=0; i<9; i+=3){
            for (int j=0; j<9; j+=3){
                List<Integer> square = new ArrayList<>();
                for (int k=0; k<3; k++){
                    for (int m=0; m<3; m++){
                        square.add(sudoku[i+k][j+m]);
                    }
                }
                if (!areAllDigitsIncluded(square)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean areAllDigitsIncluded(List<Integer> list) {
        if (list.contains(0)) { return false; }

        for (int i=1; i<10; i++) {
            if (!list.contains(i)) { return false; }
        }

        return true;
    }
}
