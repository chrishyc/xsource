#概述
![](.z_01_算法_类别_DFS_images/fdcbefbe.png)
![](.z_01_算法_类别_DFS_images/5ce07443.png)
![](.z_01_算法_类别_DFS_images/771db16e.png)
![](.z_01_算法_类别_DFS_images/8f800f2e.png)
![](.z_01_算法_类别_DFS_images/978a1188.png)
#BFS
![](.z_01_算法_类别_DFS_images/70384178.png)
![](.z_01_算法_类别_DFS_images/1d3622af.png)
![](.z_01_算法_类别_DFS_images/49b803a6.png)
##打印节点s到节点t的路径
![](.z_01_算法_类别_DFS_images/2b32dfcb.png)
#DFS(两种方式)
DFS和回溯的关系:DFS深度优先遍历,回溯主要是在深度优先遍历过程中寻找结果
一般回溯题目都是树的搜索,如果是图的搜索,也是需要记录访问过的节点
![](.z_01_算法_类别_DFS&BFS_images/f51f4e66.png)
![](.z_01_算法_类别_DFS&BFS_images/a4371452.png)
![](.z_01_算法_类别_DFS&BFS_images/5f2bb790.png)
![](.z_01_算法_类别_DFS&BFS_images/fb5d0a90.png)
回溯模板下的DFS
![](.z_01_算法_类别_DFS&BFS_images/d8d99fd9.png)
#题目类型
![](.z_01_算法_类别_DFS&BFS_images/71d76880.png)
##题型1、二维矩阵搜索或遍历 
###面试题 08.10. 颜色填充
[](https://leetcode-cn.com/problems/color-fill-lcci/)

###剑指 Offer 13. 机器人的运动范围
[](https://leetcode-cn.com/problems/ji-qi-ren-de-yun-dong-fan-wei-lcof/)
![](.z_01_算法_类别_DFS&BFS_images/e76c9ea2.png)
![](.z_01_算法_类别_DFS&BFS_images/7115264e.png)
##题型2、最短路径(BFS) 
![](.z_01_算法_类别_DFS&BFS_images/ed80fa04.png)
![](.z_01_算法_类别_DFS&BFS_images/f09a1112.png)
![](.z_01_算法_类别_DFS&BFS_images/57d06482.png)
##题型3、连通分量/连通性 
###面试题 04.01. 节点间通路
[](https://leetcode-cn.com/problems/route-between-nodes-lcci/)
###200. 岛屿数量
[](https://leetcode-cn.com/problems/number-of-islands/)
![](.z_01_算法_类别_DFS&BFS_images/1a377543.png)
![](.z_01_算法_类别_DFS&BFS_images/9c995acd.png)
###面试题 16.19. 水域大小
[](https://leetcode-cn.com/problems/pond-sizes-lcci/)
##题型4、拓扑排序 
拓扑排序
![](.z_01_算法_类别_DFS&BFS_images/487dc038.png)
![](.z_01_算法_类别_DFS&BFS_images/39ae75b1.png)
![](.z_01_算法_类别_DFS&BFS_images/064ddcf8.png)
DFS后序
![](.z_01_算法_类别_DFS&BFS_images/904cd7e4.png)
![](.z_01_算法_类别_DFS&BFS_images/6bd5582b.png)
##题型5、检测环
![](.z_01_算法_类别_DFS&BFS_images/4d561bca.png)
[](https://leetcode-cn.com/problems/course-schedule/)
```asp
class Solution {
    List<List<Integer>> edges;
    int[] visited;
    boolean valid = true;

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        edges = new ArrayList<List<Integer>>();
        for (int i = 0; i < numCourses; ++i) {
            edges.add(new ArrayList<Integer>());
        }
        visited = new int[numCourses];
        for (int[] info : prerequisites) {
            edges.get(info[1]).add(info[0]);
        }
        for (int i = 0; i < numCourses && valid; ++i) {
            if (visited[i] == 0) {
                dfs(i);
            }
        }
        return valid;
    }

    public void dfs(int u) {
        visited[u] = 1;
        for (int v: edges.get(u)) {
            if (visited[v] == 0) {
                dfs(v);
                if (!valid) {
                    return;
                }
            } else if (visited[v] == 1) {
                valid = false;
                return;
            }
        }
        visited[u] = 2;
    }
}
```
