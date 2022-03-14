package concurrent;

/**
 * 问题:有一个1G大小的一个文件，里面每一行是一个词，词的大小不超过16字节，内存限制大小是1M。返回频数最高的100个词
 * <p>
 * 1.拆分成多个子问题,子问题往往需要合并,子问题之间不能相互依赖
 * 2.拆分成子问题且不依赖,使用hash,形成多个子文件,子文件记录每个单词的频率,hash使得相同单词不可能落在几个文件中
 * 3.子文件>1M,hash冲突过大,文件的单词都不相同,需要拆分成多个小文件
 * 4.对每个子文件进行最小堆排序,取出top100
 * 5.合并每个小文件中top100,然后对总结果进行top100计算
 * <p>
 * <p>
 * 分而治之/Hash映射 + Hash_map统计 + 堆/快速/归并排序
 * <p>
 * 海量数据处理,july算法：https://blog.csdn.net/v_july_v/article/details/7382693
 */
public class y_train_4_top100_word_in_1G_with_1M {

  public static void main(String[] args) {
    read1GBigFileMapToWorkCount();
    ifFindMoreThan1MThenSplit();
    minHeapFindTop100ForEverySmallFile();
    mergeAllTop100();
    minHeapFindTop100();
  }


  private static void read1GBigFileMapToWorkCount() {
  }

  private static void ifFindMoreThan1MThenSplit() {
  }

  private static void minHeapFindTop100ForEverySmallFile() {
  }

  private static void mergeAllTop100() {
  }

  private static void minHeapFindTop100() {
  }

}
