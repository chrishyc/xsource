##k8s dashboard
kubectl proxy
[](http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/)
kubectl -n kube-system describe secret default| awk '$1=="token:"{print $2}'
[](https://blog.csdn.net/m0_48358308/article/details/111918783)
[](https://github.com/maguowei/gotok8s#helm)
##常用命令分类
kubeadm:搭建集群
kubectl:集群信息
kubelet:pod
##查看集群信息
kubectl get nodes
kubectl config view,查看集群配置
##查看命名空间资源
kubectl get pods --all-namespaces
kubectl create namespace lagou
kubectl get namespace
