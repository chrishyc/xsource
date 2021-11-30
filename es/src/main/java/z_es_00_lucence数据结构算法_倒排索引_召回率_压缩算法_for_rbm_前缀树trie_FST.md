#临界知识
高效压缩算法(索引到的倒排列表太多),快速编码解码能力
delta-encoding增量编码frame of reference
hashmap(快),skiplist(快),trieTree(小),fst
http://cs.usfca.edu
#为什么mysql不适合搜索引擎?
![](.z_es_00_搜索引擎原理_倒排索引_召回率_压缩算法_images/acb11e5e.png)
![](.z_es_00_搜索引擎原理_倒排索引_召回率_压缩算法_images/b4c72e9c.png)

#搜索引擎核心问题
![](.z_es_00_物理存储_数据结构_images/96ad1ab8.png)
#倒排索引
![](.z_es_00_搜索引擎原理_倒排索引_召回率_压缩算法_images/910137c0.png)
##term index(词索引)
##term dictionary(词项字典)
##posting list(倒排列表)

#查询速度加速(小/压缩,快/数据结构)
##压缩算法(压缩doc ID)
![](.z_es_00_搜索引擎原理_倒排索引_召回率_压缩算法_images/2647e65e.png)
###FOR(稠密压缩算法,frame of reference参考系,坐标)
适合稠密数组
差值列表deltas list
[](https://www.elastic.co/cn/blog/frame-of-reference-and-roaring-bitmaps)
![](.z_es_00_搜索引擎原理_倒排索引_召回率_压缩算法_images/aedb0f01.png)
![](.z_es_00_搜索引擎原理_倒排索引_召回率_压缩算法_images/a0cf89ea.png)
###RBM
无符号short最大值65535
![](.z_es_00_搜索引擎原理_倒排索引_召回率_压缩算法_images/9212b214.png)
####ArrayContainer
![](.z_es_00_搜索引擎原理_倒排索引_召回率_压缩算法_images/45da9492.png)
[](https://www.elastic.co/cn/blog/frame-of-reference-and-roaring-bitmaps)
####bitmapContainer
固定8KB
####RunContainer
8B
##编码解码(词项)

![](.z_es_00_lucence数据结构算法_倒排索引_召回率_压缩算法_for_rbm_前缀树trie_images/bde69ace.png)
###前缀树trie
![](.z_es_00_lucence数据结构算法_倒排索引_召回率_压缩算法_for_rbm_前缀树trie_images/400b0b36.png)
无法共享后缀
###FSA(有限状态接收机)
有向无环图,有终点信息
![](.z_es_00_lucence数据结构算法_倒排索引_召回率_压缩算法_for_rbm_前缀树trie_FST_images/514f6b3d.png)
###FST
有向无环权重图,终点有权重,动态调整权重
[](https://www.shenyanchao.cn/blog/2018/12/04/lucene-fst/)
[](https://blog.csdn.net/yians/article/details/119353272)
![](.z_es_00_lucence数据结构算法_倒排索引_召回率_压缩算法_for_rbm_前缀树trie_FST_images/cad7ab0d.png)
![](.z_es_00_lucence数据结构算法_倒排索引_召回率_压缩算法_for_rbm_前缀树trie_FST_images/2f20955a.png)
#结果准确
#检索结果丰富
召回率
