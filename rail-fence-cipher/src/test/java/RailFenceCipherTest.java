import org.junit.Test;

import static org.junit.Assert.*;

public class RailFenceCipherTest {
    @Test
    public void testEncode_ReturnEmptyString() {
        assertEquals("", RailFenceCipher.encode("", 0));
    }

    @Test
    public void testEncode_01234() {
        //given
        String stringToEncode = "01234";
        int railsNumber = 2;
        //when
        String encodedString = RailFenceCipher.encode(stringToEncode, railsNumber);
        //then
        assertEquals("02413", encodedString);
    }

    @Test
    public void testEncode_01234567890123456() {
        //given
        String stringToEncode = "01234567890123456";
        int railsNumber = 4;
        //when
        String encodedString = RailFenceCipher.encode(stringToEncode, railsNumber);
        //then
        assertEquals("06215713248046395", encodedString);
    }

    @Test
    public void testGenerateRfc() {
        //given
        /*
        0 _ _ _ _ _ 6 _ _ _ _ _ 2
        _ 1 _ _ _ 5 _ 7 _ _ _ 1 _ 3
        _ _ 2 _ 4 _ _ _ 8 _ 0 _ _ _ 4 _ 6
        _ _ _ 3 _ _ _ _ _ 9 _ _ _ _ _ 5
        */
        String stringToEncode = "01234567890123456";
        //when
        char[][] rfc = RailFenceCipher.generateRfc(4, stringToEncode);
        //then
        assertEquals('6', rfc[stringToEncode.length()-1][2]);
    }
}