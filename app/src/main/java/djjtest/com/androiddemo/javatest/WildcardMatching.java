package djjtest.com.androiddemo.javatest;

public class WildcardMatching {

    public static boolean isMatch(String s, String p) {
        char[] sc = s.toCharArray();
        char[] pc = p.toCharArray();
        return false;
    }

    public static void test() {
        System.out.println(false == isMatch("aa", "a"));
        System.out.println(true == isMatch("aa", "*"));
        System.out.println(false == isMatch("cb", "?a"));
        System.out.println(true == isMatch("adceb", "*a*b"));
        System.out.println(true == isMatch("adcebab", "*a*b"));
        System.out.println(false == isMatch("acdcb", "a*c?b"));
    }
}
