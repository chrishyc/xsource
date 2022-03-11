public class Test {
  public static void main(String[] args) {

  }

  public class Trie {
    public class Node {
      int value;
      Node left;
      Node right;
    }

    private Node root;

    // 比较当前节点,1:<cur,当前成为左子树;2:>cur,当前下移到左子树,继续循环
    // 出口:当前左子树=null,加入成为叶子结点
    public void offer(Node node) {
      if (root == null || node == null) {
        root = node;
        return;
      }
      if (root.value < node.value) {
        node.left = root;
        root = node;
      } else {
        Node tmp = root;
        Node pre = null;
        while (tmp != null && tmp.value < node.value) {
          pre = tmp;
          tmp = tmp.left;
        }
        Node lleft = pre.left;
        pre.left = node;
        node.left = lleft;
      }

    }

    public void poll() {

    }

  }
}
