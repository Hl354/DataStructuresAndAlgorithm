package algorithm;

public class SortAlgorithm {

    /**
     * 稳定性:假定在待排序的记录序列中，存在多个具有相同的关键字的记录，若经过排序，这些记录的相对次序保持不变
     * @param args
     */
    public static void main(String[] args) {
        selectionSort(new int[]{5, 2, 1, 8, 0, 11, -1});
        insertionSort(new int[]{5, 2, 1, 8, 0, 11, -1});
        bubbleSort(new int[]{5, 2, 1, 8, 0, 11, -2});
        mergeSort(new int[]{5, 2, 1, 8, 0, 12, -1});
        shellSort(new int[]{5, 2, 3, 8, 0, 12, -1, -2});
        quickSort(new int[]{5, 6, 3, 1, 0, 12, -1, 2});
        heapSort(new int[]{5, 6, 3, 1, 0, 12, -1, 2});
    }

    // 选择排序
    // 每一趟都把剩余数据最小或者最大的数值放到对应下标的位置，例如第一趟放到下标为0的位置。
    public static void selectionSort(int[] nums) {
        int tempNum = 0;
        for (int i = 0, len = nums.length; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (nums[i] > nums[j]) {
                    tempNum = nums[i];
                    nums[i] = nums[j];
                    nums[j] = tempNum;
                }
            }
        }
        System.out.println("选择排序排序结果:");
        printArray(nums);
    }

    // 插入排序
    // 每一趟都把需要插入的数据和已排序好的序列进行比较，选择合适位置插入。
    public static void insertionSort(int[] nums) {
        int tempNum = 0;
        for (int i = 0, len = nums.length; i < len; i++) {
            for (int j = 0; j <= i; j++) {
                if (nums[i] < nums[j]) {
                    tempNum = nums[i];
                    nums[i] = nums[j];
                    nums[j] = tempNum;
                }
            }
        }
        System.out.println("插入排序排序结果:");
        printArray(nums);
    }

    // 冒泡排序
    // 每一趟都把最大或者最小的值放到下标为数组长度减去趟数的位置。
    public static void bubbleSort(int[] nums) {
        int tempNum = 0;
        for (int i = 0, len = nums.length; i < len; i++) {
            for (int j = 0; j < len - i - 1; j++) {
                if (nums[j + 1] < nums[j]) {
                    tempNum = nums[j + 1];
                    nums[j + 1] = nums[j];
                    nums[j] = tempNum;
                }
            }
        }
        System.out.println("冒泡排序排序结果:");
        printArray(nums);
    }

    // 归并排序
    // 数组一分为二进行排序，再将排序好的合并
    public static void mergeSort(int[] nums) {
        splitArray(nums, 0, nums.length -1, new int[nums.length]);
        System.out.println("归并排序排序结果:");
        printArray(nums);
    }

    // 数组一分为二
    public static void splitArray(int[] nums, int start, int end, int[] tempArr) {
        int mid = (start + end) / 2;
        if (start < end) {
            splitArray(nums, start, mid, tempArr);
            splitArray(nums, mid + 1, end, tempArr);
            mergeArray(nums, start, mid, mid + 1, end, tempArr);
        }
    }

    // 合并两个有序数组
    public static void mergeArray(int[] nums, int iStart, int iEnd, int jStart, int jEnd, int[] tempArr) {
        int start = iStart, end = jEnd, tmpIndex = 0;
        while (iStart <= iEnd && jStart <= jEnd) {
            if (nums[iStart] < nums[jStart]) {
                tempArr[tmpIndex++] = nums[iStart++];
            } else {
                tempArr[tmpIndex++] = nums[jStart++];
            }
        }
        while (iStart <= iEnd) {
            tempArr[tmpIndex++] = nums[iStart++];
        }
        while (jStart <= jEnd) {
            tempArr[tmpIndex++] = nums[jStart++];
        }
        tmpIndex = 0;
        while (start <= end) {
            nums[start++] = tempArr[tmpIndex++];
        }
    }

    // 希尔排序
    // 根据步长(通常第一次为数组长度的一半，之后步长为当前步长减半，最后一次步长为一)分组数组，进行插入排序
    public static void shellSort(int[] nums) {
        int tempNum = 0;
        int increment = nums.length / 2;
        while (increment > 0) {
            for (int n = 0; n < increment; n++) {
                for (int i = n; i < nums.length; i = i + increment) {
                    for (int j = n; j < i; j = j + increment) {
                        if (nums[i] < nums[j]) {
                            tempNum = nums[i];
                            nums[i] = nums[j];
                            nums[j] = tempNum;
                        }
                    }
                }
            }
            increment = increment / 2;
        }
        System.out.println("希尔排序排序结果:");
        printArray(nums);
    }

    // 快速排序
    // 找到数组中的一个数作为标准(第一个或者最后一个)，将小于该数的值都放在左边，大于的都放在右边。
    public static void quickSort(int[] nums) {
        partition(nums, 0, nums.length - 1);
        System.out.println("快速排序排序结果:");
        printArray(nums);
    }

    // 递归将数组分割为两部分，使得左部分都小于一个值，右部分都大于一个值
    // 3 5 1 6
    public static void partition(int[] nums, int start, int end) {
        if (start >= end) return;
        int i = start, j = end - 1;
        while (i < j) {
            if (nums[i] > nums[end] && nums[j] < nums[end]) {
                swap(nums, i, j);
                i++; j--;
            } else if (nums[i] > nums[end]) {
                j--;
            } else if (nums[j] < nums[end]) {
                i++;
            }
        }
        if (i != end - 1 || i > j) swap(nums, i, end);
        partition(nums, start, i);
        partition(nums, i + 1, end);
    }

    // 堆排序
    // 升序排序构建大顶堆，每次把最大的数放到末尾
    public static void heapSort(int[] nums) {
        for (int i = nums.length - 1; i > 0 ; i--) {
            createHeap(nums, 0, i);
            swap(nums, 0, i);
        }
        System.out.println("大堆排序排序结果:");
        printArray(nums);
    }

    // 根据数组的起始和结束位置构建大顶堆
    public static void createHeap(int[] nums, int start, int end) {
        // 开始节点所在位置
        int startIndex = (end - start + 1) / 2 - 1;
        for (int i = 2 * startIndex; i >= 0; i--) {
            // 一个节点的左节点等于当前节点位置乘以二再加一
            int l = 2 * i + 1;
            // 一个节点的右节点等于当前节点位置乘以二再加二
            int r = 2 * i + 2;
            // 让三个数比较并交换，让父节点的值变为最大
            if (l <= end && nums[i] < nums[l]) {
                swap(nums, i, l);
            }
            if (r <= end && nums[i] < nums[r]) {
                swap(nums, i, r);
            }
        }
    }

    // 交换数组中的两个数
    public static void swap(int[] nums, int i, int j) {
        int tempNum = nums[i];
        nums[i] = nums[j];
        nums[j] = tempNum;
    }

    // 打印数组
    public static void printArray(int[] nums) {
        for (int i = 0, len = nums.length; i < len; i++) {
            System.out.print(nums[i] + " ");
        }
        System.out.println();
    }

}
