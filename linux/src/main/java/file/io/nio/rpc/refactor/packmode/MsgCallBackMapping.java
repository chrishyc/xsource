package file.io.nio.rpc.refactor.packmode;


import file.io.nio.rpc.refactor.rpcenv.PackageMsg;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: 马士兵教育
 * @create: 2020-07-18 11:34
 */
public class MsgCallBackMapping {
    static ConcurrentHashMap<Long, CompletableFuture> mapping = new ConcurrentHashMap<>();

    public static void  addCallBack(long requestID, CompletableFuture cb){
        mapping.putIfAbsent(requestID, cb);
    }
    public static void runCallBack(PackageMsg req){
        CompletableFuture cf = mapping.get(req.getMessageID());
        cf.complete(req.getContent().getRes());
        removeCB(req.getHeader().getRequestID());
    }

    private static void removeCB(long requestID) {
        mapping.remove(requestID);
    }

}
