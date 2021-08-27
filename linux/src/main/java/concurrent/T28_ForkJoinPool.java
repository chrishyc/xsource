/**
 * 认识Executor
 */
package concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class T28_ForkJoinPool {

  //初始化一个ForkJoinPool
  static ForkJoinPool pool = new ForkJoinPool(4,
      ForkJoinPool.defaultForkJoinWorkerThreadFactory,
      null,
      true);

  //一个集合，模拟网站
  static ArrayList<String> list = new ArrayList<>();

  //集合中的数据
  static void addList() {
    list.add("www.baidu.com");
    list.add("www.blog.csdn.net");
    list.add("www.baidu.com");
    list.add("www.blog.csdn.net");
    list.add("www.baidu.com");
    list.add("www.blog.csdn.net");
    list.add("www.baidu.com");
    list.add("www.blog.csdn.net");
    list.add("www.baidu.com");
    list.add("www.blog.csdn.net");
    list.add("www.baidu.com");
    list.add("www.blog.csdn.net");
    list.add("www.baidu.com");
    list.add("www.blog.csdn.net");
    list.add("www.baidu.com");
    list.add("www.blog.csdn.net");
    list.add("www.baidu.com");
    list.add("www.blog.csdn.net");
    list.add("www.baidu.com");
    list.add("www.blog.csdn.net");
    list.add("www.baidu.com");
    list.add("www.blog.csdn.net");
    list.add("www.baidu.com");
    list.add("www.blog.csdn.net");
    list.add("www.baidu.com");
    list.add("www.blog.csdn.net");
    list.add("www.baidu.com");
    list.add("www.blog.csdn.net");
    list.add("www.baidu.com");
    list.add("www.blog.csdn.net");
    list.add("www.baidu.com");
    list.add("www.blog.csdn.net");
    list.add("www.baidu.com");
    list.add("www.blog.csdn.net");
    list.add("www.baidu.com");
    list.add("www.blog.csdn.net");
    list.add("www.baidu.com");
    list.add("www.blog.csdn.net");
    list.add("www.baidu.com");
    list.add("www.blog.csdn.net");
    list.add("www.baidu.com");
    list.add("www.blog.csdn.net");
    list.add("www.baidu.com");
    list.add("www.blog.csdn.net");
    list.add("www.baidu.com");
    list.add("www.blog.csdn.net");
  }


  public static void main(String[] args) throws ExecutionException, InterruptedException {
    addList();
    //提交任务
    ForkJoinTask<String> task = pool.submit(new Work(list, 0, list.size()));
    System.out.println(task.get());
  }

  //模拟请求
  public static String doRequest(String url, int index) {
    return index + "--》请求测试：" + url + "\n";
  }

  //需要继承RecursiveTask，来实现自己的拆分逻辑
  static class Work extends RecursiveTask<String> {
    List<String> list;
    int start;
    int end;

    public Work(List<String> list, int start, int end) {
      this.list = list;
      this.start = start;
      this.end = end;
    }

    @Override
    protected String compute() {
      int count = end - start;
      String result = "";
      //当任务小于10个时直接执行，否则就拆分
      if (count <= 5) {
        for (int i = 0; i < list.size(); i++) {
          result += doRequest(list.get(i), i);
        }
        return result;
      } else {
        //获取任务数量索引的中间值
        int x = (start + end) / 2;
        //拆分任务
        Work work1 = new Work(list, start, x);
        work1.fork();
        //拆分任务
        Work work2 = new Work(list, x, end);
        work2.fork();
        //获取任务执行结果
        result += work1.join();
        result += work2.join();
        return result;
      }
    }
  }
}

