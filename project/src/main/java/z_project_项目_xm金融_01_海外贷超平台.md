#推荐活动
![](.z_project_项目_小米_01_小米金融海外贷超平台_images/638cab9e.png)
1.分享推荐码
2.绑定推荐码
![](.z_project_项目_小米_01_小米金融海外贷超平台_images/3fe7dce7.png)
3.授信后查看推荐关系是否存在,发放授信奖励,先存奖励表status=create,再请求rpc,再更新status=new
4.贷款后查看推荐关系是否存在,发送贷款奖励,先存奖励表status=create,再请求rpc,再更新status=new
5.人工补偿,查看推荐关系是否存在,发送授信/贷款奖励,先存奖励表status=create,再请求rpc,再更新status=new
##Mysql实现推荐奖励请求的幂等性
###为啥要幂等?
推荐活动的奖励表记录在贷超,发放奖励由支付部门的服务发放到用户账号,跨服务请求
###幂等的唯一键
mifi+referRelationId+type(奖励类型)
insert into  select from dual where not exists( select id )
```asp
insert ignore into `Test` select 'aa' , 'cao' from dual where not exists(select 1 from Test where `first_name`='aa' and `second_name`='bb');
```
###数据库幂等性
状态机+补偿性定时任务
1.insert into  status=CREATE
2.rpc request抽奖,之所以请求,因为可以由运营配置
3.rpc 请求分配金额,此时只是记录用户分配的金额,并未真正到账
4.update status=NEW
##通过事件表以及定时任务补偿机制保证了奖励更新的柔性一致性
###rocketMq异步请求一次
###定时任务扫描事件表
更新状态机
##使用短网址方案精简推荐活动URL分享
#实现activiti风控审批流的自由跳转
