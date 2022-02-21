#原理
#Java Instrument的Agent实现
##Instrument agent对象模型
[agent源码分析](https://juejin.cn/post/6844903612985032711#heading-9)
1.jvm 
2.jvm callback,JVMTI机制,Java Tool API 中的 attach,JVMTI 提供了一套”代理”程序机制，可以支持第三方工具程序以代理的方式连接和访问 JVM，
并利用 JVMTI 提供的丰富的编程接口，完成很多跟 JVM 相关的功能
3.agent包, 动态库instrument.so
4.用户自定义实现的agent jar包回调程序, -agentlib -agentpath -javaagent指定

jvm 提供了callback接口:JVMTI
```
typedef struct {
    /* 50 : VM Initialization Event */  
    jvmtiEventVMInit VMInit;   
    /* 51 : VM Death Event */  
    jvmtiEventVMDeath VMDeath; 
    /* 52 : Thread Start */  
    jvmtiEventThreadStart ThreadStart;
    /* 53 : Thread End */  
    jvmtiEventThreadEnd ThreadEnd;  
    /* 54 : Class File Load Hook */  
    jvmtiEventClassFileLoadHook ClassFileLoadHook;
    /* 55 : Class Load */  
    jvmtiEventClassLoad ClassLoad; 
    /* 56 : Class Prepare */  
    jvmtiEventClassPrepare ClassPrepare;
    /* 57 : VM Start Event */  
    jvmtiEventVMStart VMStart;
    /* 58 : Exception */  
    jvmtiEventException Exception;
    /* 59 : Exception Catch */  
    jvmtiEventExceptionCatch ExceptionCatch; 
    /* 60 : Single Step */  
    jvmtiEventSingleStep SingleStep;
    /* 61 : Frame Pop */  
    jvmtiEventFramePop FramePop;
    /* 62 : Breakpoint */  
    jvmtiEventBreakpoint Breakpoint; 
    /* 63 : Field Access */  
    jvmtiEventFieldAccess FieldAccess;
    /* 64 : Field Modification */  
    jvmtiEventFieldModification FieldModification; 
    /* 65 : Method Entry */  
    jvmtiEventMethodEntry MethodEntry;
    /* 66 : Method Exit */  
    jvmtiEventMethodExit MethodExit;
    /* 67 : Native Method Bind */  
    jvmtiEventNativeMethodBind NativeMethodBind;
    /* 68 : Compiled Method Load */  
    jvmtiEventCompiledMethodLoad CompiledMethodLoad;
    /* 69 : Compiled Method Unload */  
    jvmtiEventCompiledMethodUnload CompiledMethodUnload; 
    /* 70 : Dynamic Code Generated */  
    jvmtiEventDynamicCodeGenerated DynamicCodeGenerated; 
    /* 71 : Data Dump Request */  
    jvmtiEventDataDumpRequest DataDumpRequest;
    /* 72 */  
    jvmtiEventReserved reserved72;
    /* 73 : Monitor Wait */  
    jvmtiEventMonitorWait MonitorWait;
    /* 74 : Monitor Waited */  
    jvmtiEventMonitorWaited MonitorWaited;
    /* 75 : Monitor Contended Enter */  
    jvmtiEventMonitorContendedEnter MonitorContendedEnter;
    /* 76 : Monitor Contended Entered */  
    jvmtiEventMonitorContendedEntered MonitorContendedEntered;
    /* 77 */  
    jvmtiEventReserved reserved77;
    /* 78 */  
    jvmtiEventReserved reserved78; 
    /* 79 */  
    jvmtiEventReserved reserved79; 
    /* 80 : Resource Exhausted */  
    jvmtiEventResourceExhausted ResourceExhausted;
    /* 81 : Garbage Collection Start */  
    jvmtiEventGarbageCollectionStart GarbageCollectionStart;
    /* 82 : Garbage Collection Finish */  
    jvmtiEventGarbageCollectionFinish GarbageCollectionFinish;
    /* 83 : Object Free */  
    jvmtiEventObjectFree ObjectFree;
    /* 84 : VM Object Allocation */  
    jvmtiEventVMObjectAlloc VMObjectAlloc;  
} jvmtiEventCallbacks;  

```

##agent流程
[](/Users/chris/workspace/xsource/agent/src/main/resources/images/jvm_callback.png)
[](/Users/chris/workspace/xsource/agent/src/main/resources/images/jvm_agent.png)
[](/Users/chris/workspace/xsource/agent/src/main/resources/images/jvm_load_class.png)
##agent类型
###jvm启动前Instrumentation
```
-javaagent
-agentmain
```
###jvm运行时Instrumentation,能操作什么?如何实现操作class?
Attach API
[](https://www.jianshu.com/p/b72f66da679f)
[debug原理](https://tech.meituan.com/2019/11/07/java-dynamic-debugging-technology.html)
[](https://juejin.cn/post/6844903744266584071)
[](https://www.jianshu.com/p/86ec47435cfc)


###Java-debug-tool原理
![](https://p0.meituan.net/travelcube/810f16bba746dcf85c788037e9138a6c78210.png)
