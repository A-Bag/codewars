import static org.junit.Assert.*;
import org.junit.Test;

public class BraceCheckerTests {

    private BraceChecker checker = new BraceChecker();

    @Test
    public void testValid1() {
        assertEquals(true, checker.isValid("()"));
    }

    @Test
    public void testValid2() {
        assertEquals(true, checker.isValid("(){[()]}[][{()}]"));
    }

    @Test
    public void testInvalid1() {
        assertEquals(false, checker.isValid("[(])"));
    }

    @Test
    public void testInvalid2() {
        assertEquals(false, checker.isValid("({[()]}[][{()}]"));
    }

}
