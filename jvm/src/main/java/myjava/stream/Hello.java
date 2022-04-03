package myjava.stream;

public class Hello {
  public static void main(String[] args) {
    try {
      int a = 1;
    } catch (Exception e) {
      int b = 1;
    } finally {
      int c = 1;
    }
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
