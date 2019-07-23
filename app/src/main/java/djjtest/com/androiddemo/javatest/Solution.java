package djjtest.com.androiddemo.javatest;

import java.util.ArrayList;

public class Solution {

    public static int shortestSubarray(int[] A, int K) {
        long sum = 0;
        int count = 0;
        ArrayList<Integer> list = new ArrayList<>();
        for (int tmp : A) {
            if (tmp >= 0) {
                sum += tmp;
                count++;
            } else {
                list.add(tmp);
            }
        }
        if (sum < K) {
            return -1;
        }
        int tmp;
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size() - i - 1; j++) {
                tmp = list.get(j + 1);
                if (list.get(j) < tmp) {
                    list.set(j + 1, list.get(j));
                    list.set(j, tmp);
                }
            }
        }
//        for (int v : list) {
//            System.out.print(" " + v + " ");
//        }
        for (int tmp2 : list) {
            sum += tmp2;
            if (sum < K) {
                return count;
            }
            count++;
        }

        return count;
    }


}
