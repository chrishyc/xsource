public class A_01_selectSort {
  public int[] sortArray(int[] nums) {
    for (int i = 1; i < nums.length; i++) {
      int candidate = nums[i];
      int j = i - 1;
      for (; j >= 0; j--) {
        if (candidate < nums[j]) {
          nums[j + 1] = nums[j];
        } else {
          break;
        }
      }
      nums[j + 1] = candidate;
    }
    return nums;
  }
}
