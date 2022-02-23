package myjava.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.TimeUnit;

public class GuavaTest {
  public static void main(String[] args) {

    //创建一个LoadingCache，并可以进行一些简单的缓存配置
    LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
        //最大容量为100（基于容量进行回收）
        .maximumSize(100)
        //配置写入后多久使缓存过期-下文会讲述
        .expireAfterWrite(2, TimeUnit.SECONDS)
        //配置写入后多久刷新缓存-下文会讲述,刷新后过期时间不变
        .refreshAfterWrite(1, TimeUnit.SECONDS)
        //key使用弱引用-WeakReference
        .weakKeys()
        //当Entry被移除时的监听器
        .removalListener(notification -> {
        })
        //创建一个CacheLoader，重写load方法，以实现"当get时缓存不存在，则load，放到缓存，并返回"的效果
        .build(new CacheLoader<String, String>() {
          //重点，自动写缓存数据的方法，必须要实现
          @Override
          public String load(String key) throws Exception {
//            System.out.println("过期时间:" + System.currentTimeMillis() / 1000);
            return null;
          }

          //异步刷新缓存-下文会讲述
          @Override
          public ListenableFuture<String> reload(String key, String oldValue) throws Exception {
//            System.out.println("过期时间:" + System.currentTimeMillis() / 1000);
            return null;
          }
        });
    try {
      for (; ; ) {
        String s = loadingCache.get("1");
        System.out.println(s);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
