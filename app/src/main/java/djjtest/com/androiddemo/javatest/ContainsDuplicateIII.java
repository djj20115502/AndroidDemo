package djjtest.com.androiddemo.javatest;

public class ContainsDuplicateIII {

    static class Solution {
        public static boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
            if (nums == null || nums.length < 2) {
                return false;
            }
            if (k <= 0) {
                return false;
            }
            if (t < 0) {
                return false;
            }
            for (int i = 0; i < nums.length; i++) {
                if (two(nums, i, i + k, t)) {
                    return true;
                }
            }

            return false;
        }

        public static boolean two(int[] nums, int index, int end, long t) {
            if (end >= nums.length) {
                end = nums.length - 1;
            }
            long cha = 0;
            for (int i = index + 1; i <= end; i++) {
                cha = (long) nums[i] - (long) nums[index];
                if (cha <= t && cha >= -t) {
                    return true;
                }
            }
            return false;
        }
    }


    public static void test() {
        System.out.println(Solution.containsNearbyAlmostDuplicate(new int[]{1, 2, 3, 1}, 3, 0));
        System.out.println(Solution.containsNearbyAlmostDuplicate(new int[]{1, 0, 1, 1}, 1, 2));
        System.out.println(Solution.containsNearbyAlmostDuplicate(new int[]{1, 5, 9, 1, 5, 9}, 2, 4));
        System.out.println(Solution.containsNearbyAlmostDuplicate(new int[]{0}, 0, 0));
        System.out.println(Solution.containsNearbyAlmostDuplicate(new int[]{2147483647, -2147483647}, 1, 2147483647));

    }
}
