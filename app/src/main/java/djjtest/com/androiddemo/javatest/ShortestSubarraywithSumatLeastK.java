package djjtest.com.androiddemo.javatest;

import java.util.Deque;
import java.util.LinkedList;

/**
 * https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/
 */
public class ShortestSubarraywithSumatLeastK {

    public static int shortestSubarray(int[] A, int K) {
        int N = A.length;
        long[] P = new long[N + 1];
        for (int i = 0; i < N; ++i)
            P[i + 1] = P[i] + (long) A[i];

        int ans = N + 1;
        Deque<Integer> monoq = new LinkedList<>();

        for (int y = 0; y < P.length; ++y) {
            while (!monoq.isEmpty() && P[y] <= P[monoq.getLast()])
                monoq.removeLast();
            while (!monoq.isEmpty() && P[y] >= P[monoq.getFirst()] + K)
                ans = Math.min(ans, y - monoq.removeFirst());

            monoq.addLast(y);
        }

        return ans < N + 1 ? ans : -1;
    }


    public static void test() {
        Main.print(1, shortestSubarray(new int[]{1}, 1));
        Main.print(-1, shortestSubarray(new int[]{1, 2}, 4));
        Main.print(3, shortestSubarray(new int[]{2, -1, 2}, 3));
        Main.print(1, shortestSubarray(new int[]{77, 19, 35, 10, -14}, 19));
        Main.print(2, shortestSubarray(new int[]{48, 99, 37, 4, -31}, 140));
        Main.print(4, shortestSubarray(new int[]{5, 6, -1, 3}, 13));
        Main.print(4, shortestSubarray(new int[]{5, 6, -1, -2, 3, 3,}, 13));
    }


    public static int[] StringToInt(String s) {
        String[] split = s.split(",");
        int[] rt = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            rt[i] = Integer.valueOf(split[i]);
        }
        return rt;
    }

}
