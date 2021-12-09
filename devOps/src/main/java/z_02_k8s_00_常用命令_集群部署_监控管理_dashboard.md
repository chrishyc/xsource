##k8s dashboard
kubectl proxy
[](http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/)
kubectl -n kube-system describe secret default| awk '$1=="token:"{print $2}'
[](https://blog.csdn.net/m0_48358308/article/details/111918783)
[](https://github.com/maguowei/gotok8s#helm)
##kubeadm安装集群
[](https://developer.aliyun.com/article/642708)
macos不支持
##常用命令分类
kubeadm:搭建集群
kubectl:集群信息
##查看集群信息
kubectl get nodes
kubectl config view,查看集群配置
##查看命名空间资源
kubectl get pods --all-namespaces
kubectl create namespace lagou
kubectl get namespace
##pod操作
查看default命名空间下的pods 
kubectl get pods 
查看kube-system命名空间下的pods 
kubectl get pods -n kube-system 
查看所有命名空间下的pods
kubectl get pod --all-namespaces
kubectl get pod -A

kubectl get pod -o wide,//查看ip

kubectl delete pod podName,//删除后会被deployment controller重建

kubectl get pods -A,//查看所有命名空间的pod

[1]kubectl run k8s-grafana  --image=grafana/grafana:latest --port=3000//创建

###查看pod在哪些节点运行
kubectl get pod -A -o yaml |grep '^    n'|grep -v nodeSelector|sed 'N;N;s/\n/ /g'|grep -v kube-system
###查看pod对应哪些deploy
##deployment操作
[2]kubectl create deployment k8s-grafana --image=grafana/grafana:latest

kubectl get deployments
kubectl scale --replicas=0 deployment/<your-deployment>
kubectl delete deployments.apps my-tomcat//删除pod
[3]kubectl expose deployment k8s-grafana --name=default --port=3001(集群内端口) --target-port=3000(容器内部端口) --protocol=TCP --type=NodePort，//创建service,对外暴露端口



##service
kubectl get service,//获取对外端口
kubectl get svc，//service缩写

kubectl get svc -o wide,//
```asp
NAME         TYPE           CLUSTER-IP       EXTERNAL-IP   PORT(S)          AGE
default      NodePort       10.98.206.235    <none>        3001:31335/TCP   49s
kubernetes   ClusterIP      10.96.0.1        <none>        443/TCP          23d
my-tomcat    LoadBalancer   10.101.252.187   localhost     8000:30131/TCP   5d4h
```
31335为外部访问端口
##kubectl扩容
kubectl scale --replicas=3 deployment/tomcat9-test
