package algorithm;

public class FindingAlgorithm {

    public static void main(String[] args) {
        int[] disorderNums = {5, 2, 1, 8, 0, 11, -1};
        System.out.println("直接查找:" + directFinding(disorderNums, 1));
        int[] orderedNums = {1, 2, 3, 4, 5, 6};
        System.out.println("二分查找:" + directFinding(orderedNums, 7));
    }

    // 直接查找
    public static int directFinding(int[] nums, int num) {
        for (int i = 0, len = nums.length; i < len; i++) {
            if (nums[i] == num) {
                return i;
            }
        }
        return -1;
    }

    // 二分查找有序数组
    public static int binaryFinding(int[] nums, int num) {
        return recursion(nums, num, 0, nums.length - 1);
    }

    public static int recursion(int[] nums, int num, int start, int end) {
        int mid = (end + start) / 2;
        if (start == end && nums[mid] != num) return -1;
        else if (nums[mid] < num) return recursion(nums, num, start, mid);
        else if (nums[mid] > num) return recursion(nums, num, mid + 1, end);
        else return mid;
    }

}
