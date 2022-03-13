public class A_01_mergeSort {
  public int[] sortArray(int[] nums) {
    mergeSort(nums, 0, nums.length - 1);
    return nums;
  }

  // 1.mergesort
  // 2.merge,左边右边合并,
  public void mergeSort(int[] nums, int left, int right) {
    if (left >= right) return;
    int mid = (right - left) / 2 + left;
    mergeSort(nums, left, mid);
    mergeSort(nums, mid + 1, right);
    merge(nums, left, mid, right);
  }

  public void merge(int[] nums, int left, int mid, int right) {
    int l1 = left;
    int l2 = mid + 1;
    int[] tmp = new int[right - left + 1];
    int i = 0;
    while (l1 <= mid && l2 <= right) {
      if (nums[l1] < nums[l2]) {
        tmp[i++] = nums[l1++];
      } else {
        tmp[i++] = nums[l2++];
      }
    }
    while (l1 <= mid) tmp[i++] = nums[l1++];
    while (l2 <= right) tmp[i++] = nums[l2++];
    for (int j = left; j <= right; j++) {
      nums[j] = tmp[j - left];
    }
  }
}
