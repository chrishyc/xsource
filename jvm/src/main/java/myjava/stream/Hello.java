package myjava.stream;

public class Hello {
  public static void main(String[] args) {
    System.out.println(count(4));
  }


  public class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
      this.val = val;
    }

    ListNode(int val, ListNode next) {
      this.val = val;
      this.next = next;
    }
  }

  public ListNode reverseKGroup(ListNode head, int k) {
    ListNode dummpy = new ListNode();
    dummpy.next = head;
    ListNode tail = dummpy;
    ListNode cur = head;
    while (cur != null) {
      ListNode curr = cur;
      int count = k;
      while (count > 0 && curr != null) {
        curr = curr.next;
        count--;
      }
      if (curr == null) return dummpy.next;
      ListNode p = cur;
      ListNode q = curr.next;
      ListNode[] arr = reverse(p, q);
      tail.next = arr[0];
      tail = arr[1];
      cur = curr.next;
    }
    return dummpy.next;
  }

  public static int count(int n) {
    int[] arr = new int[n + 1];
    arr[0] = 1;
    arr[1] = 2;
    arr[2] = 4;
    arr[3] = 7;
    for (int i = 4; i <= n; i++) {
      arr[i] = arr[i - 1] * 2 - arr[i - 4];
    }
    return arr[n];
  }

  public static void recur(int[] arr, int start, int n) {
    if (start > n) return;
    arr[0] = arr[1];
    arr[1] = arr[2];
    arr[2] = arr[3];
    arr[3] = arr[2] * 2;
    if (start >= 4) arr[3] -= arr[0];
    recur(arr, start + 1, n);
  }


  public ListNode[] reverse(ListNode p, ListNode q) {
    ListNode dummpy = new ListNode();
    ListNode cur = dummpy;
    ListNode tail = p;
    while (cur != q) {
      ListNode next = p.next;
      p.next = cur.next;
      cur.next = p;
      cur = p;
      p = next;
    }
    return new ListNode[]{dummpy.next, tail};
  }

}
