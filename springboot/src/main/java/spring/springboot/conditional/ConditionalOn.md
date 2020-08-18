@ConditionalOn**系列逻辑
##1.需求
满足一定条件时,@component组件才能注入bean工厂中.条件可能是OnBean,OnClass,OnJava,OnJndi,OnMissingBean,OnProperty等
##2.方案:
容器在扫描@Component类时,先查看是否满足条件,满足条件才生成对应的BeanDefinition,然后加入容器中.
##3.实现:
1.过程:
ClassPathScanningCandidateComponentProvider.isCandidateComponent()->
判断是否是候选组件(@Component为候选组件)
TypeFilter.match()->
判断候选组件是否满足条件(@ConditionalOn)
ConditionEvaluator.shouldSkip()->
获取@Conditional注解中申明的On**Condition类名
ConditionEvaluator.getConditionClasses()->
实例化On**Condition类,并调用On**Condition类的matches方法
OnClassCondition.matches()->
获取@OnClassCondition注解中候选注解信息
OnClassCondition.getCandidates()->
通过反射注解class判断路径上是否存在该class
OnClassCondition.filter()->
返回匹配结果
ConditionOutcome,ConditionOutcome.match()->

2.关键:
SpringBootCondition.matches()中获取OnClassCondition.getMatchOutcome()

工具类:ConditionEvaluator,OutcomesResolver
上下文类:ConditionContextImpl
核心模板类:SpringBootCondition
实体类:ConditionOutcome,ConditionMessage

3.设计模式:
SpringBootCondition模板方法
接口集合遍历includeFilters

4.数据结构:
MultiValueMap<String, Object>
SortedMap

5.语法
Condition... conditions
方法final:public final boolean matches
private ConditionEvaluationReport()
final class
final List<String> exclusions
单例:public static final AnnotationAwareOrderComparator INSTANCE
@Nullable

6.命名:
metadata
ConditionContext
AnnotationMetadata
MethodMetadata
ClassMetadata
DeclaringClassName
Evaluation
ConditionOutcome
AncestorsMatched
ConditionEvaluationReport
ConditionEvaluator
deduceBeanFactory
shouldSkip
Candidate
resolve

7.检测:
Log logger
