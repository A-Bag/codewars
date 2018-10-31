class RailFenceCipher {
    static String encode(String s, int n) {
        if (s.isEmpty()) {
            return "";
        }

        char[][] rfc = generateRfc(n, s);

        StringBuilder stringBuilder = new StringBuilder();

        for (int depth=0; depth<n; depth++) {
            for (int width=0; width<s.length(); width++) {
                char letter = rfc[width][depth];
                if ((int)letter != 0) {
                    stringBuilder.append(letter);
                }
            }
        }

        return stringBuilder.toString();
    }

    static char[][] generateRfc(int rfcDepth, String string) {
        int rfcWidth = string.length();
        char[][] rfc = new char[rfcWidth][rfcDepth];
        for (int i=0; i<rfcWidth; i++) {
            if ((i / (rfcDepth-1)) % 2 == 0) {
                rfc[i][i%(rfcDepth-1)] = string.charAt(i);
            } else {
                rfc[i][rfcDepth-1-i%(rfcDepth-1)] = string.charAt(i);
            }
        }
        return rfc;
    }
}
