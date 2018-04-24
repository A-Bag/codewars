public class Conversion {

    public String solution(int n) {
        String reversedNumber = reverseDigitsOrder(n);
        StringBuilder result = new StringBuilder();

        String[] romanNumbers = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX",
                                "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC",
                                "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM",
                                "M", "MM", "MMM"};

        for (int i=0; i<reversedNumber.length(); i++){
            for (int j=1; j<10; j++){
                if (Character.getNumericValue(reversedNumber.charAt(i))==j) {
                    result = result.insert(0,romanNumbers[9*i + j-1]);
                }
            }
        }

        return result.toString();
    }

    public static String reverseDigitsOrder (int n) {
        String number = Integer.toString(n);
        StringBuilder result = new StringBuilder();
        for (int i=number.length()-1; i>=0; i--) {
            result.append(number.charAt(i));
        }
        return result.toString();
    }
}
