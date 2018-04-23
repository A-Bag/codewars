import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SumOfKTest {

    @Test
    public void BasicTests1() {
        System.out.println("Test no 1");
        List<Integer> ts = new ArrayList<>(Arrays.asList(50, 55, 56, 57, 58));
        int n = SumOfK.chooseBestSum(163, 3, ts);
        assertEquals(163, n);
    }

    @Test
    public void BasicTests2() {
        System.out.println("Test no 2");
        List<Integer> ts = new ArrayList<>(Arrays.asList(50));
        Integer m = SumOfK.chooseBestSum(163, 3, ts);
        assertEquals(null, m);
    }

    @Test
    public void BasicTests3() {
        System.out.println("Test no 3");
        List<Integer> ts = new ArrayList<>(Arrays.asList(91, 74, 73, 85, 73, 81, 87));
        int n = SumOfK.chooseBestSum(230, 3, ts);
        assertEquals(228, n);
    }

    @Test
    public void BasicTests4() {
        System.out.println("Test no 4");
        List<Integer> ts = new ArrayList<>(Arrays.asList(0, 350, 350, 0));
        int n = SumOfK.chooseBestSum(800, 2, ts);
        assertEquals(700, n);
    }
}