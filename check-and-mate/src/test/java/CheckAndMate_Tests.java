import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.*;
import org.junit.Test;

public class CheckAndMate_Tests {
    @Test
    public void test_isCheck_PawnThreatensKing() {
        PieceConfig[] game = new PieceConfig[] { new PieceConfig("king", 1, 4, 0),
                new PieceConfig("king", 0, 4, 7),
                new PieceConfig("pawn", 1, 5, 6)};
        //OutputBoard.print(game);
        Set<PieceConfig> expected = new HashSet<PieceConfig>(Arrays.asList(game[2]));
        assertEquals("Pawn threatens king", expected, CheckAndMate.isCheck(game, 0));
    }

    @Test
    public void test_isCheck_RookThreatensKing() {
        PieceConfig[] game = new PieceConfig[] { new PieceConfig("king", 1, 4, 0),
                new PieceConfig("king", 0, 4, 7),
                new PieceConfig("rook", 1, 4, 1)};
        //OutputBoard.print(game);
        Set<PieceConfig> expected = new HashSet<PieceConfig>(Arrays.asList(game[2]));
        assertEquals("Rook threatens king", expected, CheckAndMate.isCheck(game, 0));
    }

    @Test
    public void test_isCheck_KnightThreatensKing() {
        PieceConfig[] game = new PieceConfig[] { new PieceConfig("king", 1, 4, 0),
                new PieceConfig("king", 0, 4, 7),
                new PieceConfig("knight", 1, 2, 6)};
        //OutputBoard.print(game);
        Set<PieceConfig> expected = new HashSet<PieceConfig>(Arrays.asList(game[2]));
        assertEquals("Knight threatens king", expected, CheckAndMate.isCheck(game, 0));
    }

    @Test
    public void test_isCheck_BishopThreatensKing() {
        PieceConfig[] game = new PieceConfig[] { new PieceConfig("king", 1, 4, 0),
                new PieceConfig("king", 0, 4, 7),
                new PieceConfig("bishop", 1, 0, 3)};
        //OutputBoard.print(game);
        Set<PieceConfig> expected = new HashSet<PieceConfig>(Arrays.asList(game[2]));
        assertEquals("Bishop threatens king", expected, CheckAndMate.isCheck(game, 0));
    }

    @Test
    public void test_isCheck_QueenThreatensKing1() {
        PieceConfig[] game = new PieceConfig[] { new PieceConfig("king", 1, 4, 0),
                new PieceConfig("king", 0, 4, 7),
                new PieceConfig("queen", 1, 4, 1)};
        //OutputBoard.print(game);
        Set<PieceConfig> expected = new HashSet<PieceConfig>(Arrays.asList(game[2]));
        assertEquals("Queen threatens king", expected, CheckAndMate.isCheck(game, 0));
    }

    @Test
    public void test_isCheck_QueenThreatensKing2() {
        PieceConfig[] game = new PieceConfig[] { new PieceConfig("king", 1, 4, 0),
                new PieceConfig("king", 0, 4, 7),
                new PieceConfig("queen", 1, 7, 4)};
        //OutputBoard.print(game);
        Set<PieceConfig> expected = new HashSet<PieceConfig>(Arrays.asList(game[2]));
        assertEquals("Queen threatens king", expected, CheckAndMate.isCheck(game, 0));
    }


    @Test
    public void test_isCheck_DoubleThreat() {
        PieceConfig[] game = new PieceConfig[] { new PieceConfig("king", 1, 4, 0),
                new PieceConfig("pawn", 0, 4, 6),
                new PieceConfig("pawn", 0, 5, 6),
                new PieceConfig("king", 0, 4, 7),
                new PieceConfig("bishop", 0, 5, 7),
                new PieceConfig("bishop", 1, 1, 4),
                new PieceConfig("rook", 1, 2, 7, 2, 5)};
        //OutputBoard.print(game);
        Set<PieceConfig> expected = new HashSet<PieceConfig>(Arrays.asList(game[5], game[6]));
        assertEquals("Double threat", expected, CheckAndMate.isCheck(game, 0));
    }
}
