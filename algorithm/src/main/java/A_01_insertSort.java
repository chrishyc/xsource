public class A_01_insertSort {
  public int[] sortArray(int[] nums) {
    // 两个部分
    // 左边有序,右边待处理
    // 外层for 1,n
    // 内层:一轮比较大小,拿到最小的index
    // 外层,交换
    for (int i = 0; i < nums.length - 1; i++) {
      int minIndex = i;
      for (int j = i + 1; j < nums.length; j++) {
        if (nums[j] < nums[minIndex]) {
          minIndex = j;
        }
      }
      swap(nums, i, minIndex);
    }
    return nums;
  }

  public void swap(int[] nums, int a, int b) {
    int temp = nums[a];
    nums[a] = nums[b];
    nums[b] = temp;
  }
}
