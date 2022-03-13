public class A_01_quickSort {
  public int[] sortArray(int[] nums) {
    quickSort(nums, 0, nums.length - 1);
    return nums;
  }

  // 快排
  // 1.partition(arr,l,q,r),右边作为pivot,l->r-1;l<r-1;内部也是l<r-1;最后外部swap一次
  // 2.外层
  public void quickSort(int[] nums, int left, int right) {
    if (left >= right) return;
    int pivot = partition(nums, left, right);
    quickSort(nums, left, pivot - 1);
    quickSort(nums, pivot + 1, right);
  }

  //从小到大
  public int partition(int[] nums, int left, int right) {
    int start = left;
    int end = right - 1;
    while (start < end) {
      while (start < end && nums[start] < nums[right]) start++;
      while (start < end && nums[end] >= nums[right]) end--;
      if (start < end) {
        swap(nums, start, end);
        start++;
        end--;
      }
    }
    // 此情况是end-start=1;
    // 情况1:start,1,end,5
    // 情况2:start,6,end,5
    if (end == start && nums[end] > nums[right]) {
      swap(nums, end, right);
      return end;
      // 此情况是end<start
    } else {
      swap(nums, end + 1, right);
      return end + 1;
    }
  }

  public void swap(int[] nums, int a, int b) {
    int temp = nums[a];
    nums[a] = nums[b];
    nums[b] = temp;
  }
}
