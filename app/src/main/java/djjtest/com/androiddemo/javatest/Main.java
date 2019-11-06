package djjtest.com.androiddemo.javatest;

public class Main {

    public static void main(String[] arg) {
//        ContainsDuplicateIII.test();

        ShortestSubarraywithSumatLeastK.test();
//        print(-34|1);
//        print(-34 & 3);
//        print( 34 & 3);
//        print( 1 ^ 1);
//        print( -1 ^ 1);
//        print( 1 ^ -1);
//        print( -1 ^ -1);
//        print( -34 ^ 0);
//        print(  34 ^ 0);
//        print( 0 ^ 0);
//        print( 0 ^ 3);
//        print( 0 ^ -3);

    }


    public static void print(Object... log) {

        StringBuilder stringBuilder = new StringBuilder();
        for (Object s : log) {
            stringBuilder.append(s).append(" ");
        }
        System.out.println(stringBuilder.toString());
    }

}
