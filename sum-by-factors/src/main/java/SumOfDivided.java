import java.util.ArrayList;
import java.util.List;

public class SumOfDivided {
    public static String sumOfDivided(int[] l) {
        List<Integer[]> pairsOfPrimeFactorAndDividend = generateListOfPairs(l);
        pairsOfPrimeFactorAndDividend.sort((Integer[] pair1, Integer[] pair2) -> pair1[0].compareTo(pair2[0]));
        for (int i=0; i<pairsOfPrimeFactorAndDividend.size(); i++) {
            if (i==0) {continue;}
            Integer[] currentPair = pairsOfPrimeFactorAndDividend.get(i);
            Integer[] previousPair = pairsOfPrimeFactorAndDividend.get(i-1);
            if (currentPair[0] == previousPair[0]) {
                previousPair[1] += currentPair[1];
                pairsOfPrimeFactorAndDividend.remove(i);
                i--;
            }
        }

        StringBuilder result = new StringBuilder();
        for (Integer[] pair : pairsOfPrimeFactorAndDividend) {
            result.append("(" + pair[0] + " " + pair[1] + ")");
        }
        return result.toString();
    }

    private static List<Integer[]> generateListOfPairs (int... numbers) {
        List<Integer[]> result = new ArrayList<>();
        for (int num : numbers) {
            for (int i=2; i<=num; i++) {
                if (num % i == 0 && isPrime(i)) {
                    Integer[] pair = {i, num};
                    result.add(pair);
                }
            }
        }
        return result;
    }


    private static boolean isPrime (int factor) {
        if (factor < 0) { factor = -factor; }
        for (int i=2; i<factor; i++) {
            if (factor % i == 0) {
                return false;
            }
        }
        return true;
    }
}
