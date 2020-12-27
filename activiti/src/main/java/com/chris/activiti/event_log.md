##日志监听体系,方案设计,输入&输出&数据
通过事件订阅发布机制,监听事件类型,并记录日志.根据事件类型获取对应事件处理器来处理日志.
输入:ActivitiEvent
输出:EventLogEntryEntity
处理器:map+handler
日志清洗:eventflusher

2.设计模式
模板方法


3.涉及对象
事件:ActivitiEvent
事件处理器:EventLoggerEventHandler
日志输出:EventLogEntryEntity
日志清洗器:EventFlusher

事件监听器:ActivitiEventListener(EventLogger)
指令上下文结束监听器:CommandContextCloseListener(EventFlusher)

指定上下文:CommandContext

4.默认行为
5.自定义行为
