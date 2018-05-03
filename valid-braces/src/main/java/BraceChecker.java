public class BraceChecker {
    public boolean isValid(String braces) {
        while (braces.contains("()") || braces.contains("[]") || braces.contains("{}")) {
            if (braces.contains("()")) {
                braces = braces.substring(0, braces.indexOf("()"))
                        + braces.substring(braces.indexOf("()") + 2, braces.length());
            }
            if (braces.contains("{}")) {
                braces = braces.substring(0, braces.indexOf("{}"))
                        + braces.substring(braces.indexOf("{}") + 2, braces.length());
            }
            if (braces.contains("[]")) {
                braces = braces.substring(0, braces.indexOf("[]"))
                        + braces.substring(braces.indexOf("[]") + 2, braces.length());
            }
        }

        return braces.isEmpty();
    }
}
