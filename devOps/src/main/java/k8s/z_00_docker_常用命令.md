##k8s dashboard
kubectl proxy
[](http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/)
kubectl -n kube-system describe secret default| awk '$1=="token:"{print $2}'
[](https://blog.csdn.net/m0_48358308/article/details/111918783)
