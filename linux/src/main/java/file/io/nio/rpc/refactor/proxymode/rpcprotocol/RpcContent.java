package file.io.nio.rpc.refactor.proxymode.rpcprotocol;


import java.io.Serializable;

/**
 * @author: 马士兵教育
 * @create: 2020-07-18 11:30
 */
public class RpcContent implements Serializable {

    String name;
    String methodName;
    Class<?>[] parameterTypes;
    Object[] args;
    String res;

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public RpcContent(String s) {

        res =s;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public static RpcContent createContent(String serviceName,String methodName,Object[] args,Class<?>[] parameterTypes){
        RpcContent content = new RpcContent("send..msg");
        content.setArgs(args);
        content.setName(serviceName);
        content.setMethodName(methodName);
        content.setParameterTypes(parameterTypes);
        return content;

    }
}
