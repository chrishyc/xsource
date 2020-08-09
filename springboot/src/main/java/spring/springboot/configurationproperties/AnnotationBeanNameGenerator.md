AnnotationBeanNameGenerator
##1.需求:
注解bean命名策略
##2.方案:
如果是org.springframework.stereotype.Component,
查看是否有value,否则使用默认名称
##3.实现:
1.关键:
```
protected String determineBeanNameFromAnnotation(AnnotatedBeanDefinition annotatedDef) {
		AnnotationMetadata amd = annotatedDef.getMetadata();
		Set<String> types = amd.getAnnotationTypes();
		String beanName = null;
		for (String type : types) {
			AnnotationAttributes attributes = AnnotationConfigUtils.attributesFor(amd, type);
			if (attributes != null && isStereotypeWithNameValue(type, amd.getMetaAnnotationTypes(type), attributes)) {
				Object value = attributes.get("value");
				if (value instanceof String) {
					String strVal = (String) value;
					if (StringUtils.hasLength(strVal)) {
						if (beanName != null && !strVal.equals(beanName)) {
							throw new IllegalStateException("Stereotype annotations suggest inconsistent " +
									"component names: '" + beanName + "' versus '" + strVal + "'");
						}
						beanName = strVal;
					}
				}
			}
		}
		return beanName;
	}
```
2.语法:
单例:
public static final AnnotationBeanNameGenerator INSTANCE = new AnnotationBeanNameGenerator();
内省:Introspector
元数据:AnnotationMetadata
方法命名:
Generator,generateBeanName,determineBeanName,isStereotypeWithNameValue,buildDefaultBeanName,decapitalize
