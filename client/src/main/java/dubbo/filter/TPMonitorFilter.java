//package dubbo.filter;
//
//import org.apache.dubbo.common.constants.CommonConstants;
//import org.apache.dubbo.common.extension.Activate;
//import org.apache.dubbo.rpc.*;
//
//import java.util.*;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//@Activate(group = CommonConstants.CONSUMER)
//public class TPMonitorFilter implements Filter {
//
//    private final Map<String, Queue<Pair<Long, Long>>> methodMonitors = new HashMap<>();
//
//    {
//        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
//        executorService.scheduleAtFixedRate(() -> methodMonitors.forEach((k, v) -> {
//            int methodTimeRecords = v.size();
//            long curTimeStamp = System.currentTimeMillis();
//            PriorityQueue<Long> priorityQueue = new PriorityQueue<>();
//            while (methodTimeRecords > 0) {
//                Pair<Long, Long> node = v.poll();
//                if (curTimeStamp - node.fst < 60 * 1000) {
//                    v.offer(node);
//                    priorityQueue.offer(node.snd);
//                }
//                methodTimeRecords--;
//            }
//            int oneMinRecords = priorityQueue.size();
//            int tp99 = (int) (oneMinRecords * 0.99);
//            int tp90 = (int) (oneMinRecords * 0.99);
//            while (oneMinRecords > 0) {
//                Long time = priorityQueue.poll();
//                if (oneMinRecords == tp99) {
//                    System.out.println("in last one minute,tp99 of method " + k + " is " + time);
//                }
//
//                if (oneMinRecords == tp90) {
//                    System.out.println("in last one minute,tp90 of method " + k + " is " + time);
//                }
//                oneMinRecords--;
//            }
//        }), 5L, 5L, TimeUnit.SECONDS);
//    }
//
//    @Override
//    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
//        Result result;
//        if (invocation.getMethodName().startsWith("monitorMethod")) {
//            long start = System.currentTimeMillis();
//            result = invoker.invoke(invocation);
//            long end = System.currentTimeMillis();
//            Queue<Pair<Long, Long>> methodMonitor = methodMonitors.computeIfAbsent(invocation.getMethodName(), i -> new LinkedList<>());
//            methodMonitor.offer(new Pair<>(end, end - start));
//        } else {
//            result = invoker.invoke(invocation);
//        }
//        return result;
//    }
//}
