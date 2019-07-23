package djjtest.com.androiddemo.javatest;

public class Main {

    public static void main(String[] arg) {

        println(new int[]{1}, 1) ;
        println(new int[]{1, 2}, 4)  ;
        println(new int[]{2, -1, 2}, 3) ;
        println(new int[]{2, -1, 4,5,6,7,-23,-2}, 3) ;
        println(new int[]{77,19,35,10,-14}, 19) ;

    }

    public static void println(int[] A, int K) {
        long t1 = System.currentTimeMillis();
        int ans = Solution.shortestSubarray(A, K);
        System.out.println("time: "+(System.currentTimeMillis() - t1) + "  ans:" + ans);
    }
}
