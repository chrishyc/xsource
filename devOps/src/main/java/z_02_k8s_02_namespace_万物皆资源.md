#namespace
```asp
多租户情况下，实现资源隔离 属于逻辑隔离
属于管理边界
不属于网络边界 可以针对每个namespace做资源配额

Kubernetes 支持多个虚拟集群，它们底层依赖于同一个物理集群。 这些虚拟集群被称为名字空间。 在一些文档里名字空间也称为命名空间
```
[](https://kubernetes.io/zh/docs/concepts/overview/working-with-objects/namespaces/)
