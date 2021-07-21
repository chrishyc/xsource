package datastruct8Algorithm;

import org.junit.Test;

import java.util.Arrays;

public class QuickSort {
    @Test
    public void sortArray() {
        int[] nums = new int[]{5, 1, 1, 2, 0, 0};
        quickSort(nums, 0, nums.length - 1);
        System.out.print(Arrays.toString(nums));
    }
    
    public void quickSort(int[] nums, int start, int end) {
        if (start >= end) return;
        int povit = patition(nums, start, end);
        quickSort(nums, start, povit - 1);
        quickSort(nums, povit + 1, end);
    }
    
    public int patition(int[] nums, int low, int high) {
        int pivot = nums[low];
        int start = low;
        while (low < high) {
            while (low < high && nums[high] >= pivot) high--;//high在前确保nums[low] <= pivot
            while (low < high && nums[low] <= pivot) low++;//7,2,这种情况不能low++
            if (low >= high) break;
            swap(nums, low, high);
            //会出错,low有可能是要找的(不需要交换时)，有可能需要-1(需要交换时)
//            low++;
//            high--;
        
        }
        swap(nums, start, low);
        return low;
    }
    
    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    @Test
    public void test() {
        System.out.print(Math.pow(10, 10) > Integer.MAX_VALUE);
        System.out.print(Math.ceil(5 / (double) 2));
    }
}
