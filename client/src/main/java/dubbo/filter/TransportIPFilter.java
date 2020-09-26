package dubbo.filter;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Activate(group = "consumer")
public class TransportIPFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            try {
                RpcContext.getContext().setAttachment("clientIp", InetAddress.getLocalHost().getHostAddress());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            return invoker.invoke(invocation);
        } finally {
            RpcContext.getContext().clearAttachments();
        }
    }
}
