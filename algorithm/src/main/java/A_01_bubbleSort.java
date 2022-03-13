public class A_01_bubbleSort {
  public int[] sortArray(int[] nums) {
    for (int i = 0; i < nums.length; i++) {
      boolean flag = false;
      for (int j = 0; j < nums.length - i - 1; j++) {
        if (nums[j] > nums[j + 1]) {
          swap(nums, j, j + 1);
          flag = true;
        }
      }
      if (!flag) break;
    }
    return nums;
  }

  public void swap(int[] nums, int a, int b) {
    int temp = nums[a];
    nums[a] = nums[b];
    nums[b] = temp;
  }
}
