public class A_10_Heap {
  private int[] arr;
  private int n;
  private int count;

  public A_10_Heap(int capacity) {
    arr = new int[capacity + 1];
    n = capacity;
    count = 0;
  }

  // 放入尾部
  // 自下而上比较
  // 出口i/2>0&&arr[i/2]<arr[i];swap,i=i/2;
  public void insert(int a) {
    if (count >= n) return;
    count++;
    arr[count] = a;
    int i = count;
    while (i / 2 > 0 && arr[i / 2] < arr[i]) {
      swap(arr, i / 2, i);
      i = i / 2;
    }
  }

  // 删除头部
  // 将尾部元素放入头部
  // 自上而下比较,左边i*2<=count&>,记录位置,右边i*2+1<=count&<,记录位置
  // 最终的位置和当前进行交换,继续
  // 出口:i==max,退出循环
  public void remove() {
    if (count == 0) return;
    arr[1] = arr[count--];
    int i = 1;
    heapify(arr, count, 1);
  }

  public void heapify(int[] arr, int n, int i) {
    while (true) {
      int maxIndex = i;
      if (i * 2 <= count && arr[i] < arr[i * 2]) maxIndex = i * 2;
      if (i * 2 + 1 <= count && arr[i] < arr[i * 2 + 1]) maxIndex = i * 2 + 1;
      if (i == maxIndex) break;
      swap(arr, i, maxIndex);
      i = maxIndex;
    }
  }

  // n/2开始到1结束
  // 对每个元素,自上而下堆化
  public void buildHeap(int[] arr, int n) {
    for (int i = n / 2; i >= 1; i--) {
      heapify(arr, count, i);
    }
  }

  // 交换 <1,count>
  // count--
  // 堆化
  public void sort(int[] arr, int n) {
    if (n == 0) return;
    int k = n;
    while (k > 0) {
      swap(arr, 1, k);
      k--;
      heapify(arr, k, 1);
    }

  }

  public void swap(int[] arr, int a, int b) {
    int temp = arr[a];
    arr[a] = arr[b];
    arr[b] = temp;
  }
}
