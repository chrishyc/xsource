ResolvableType
需求:
将type泛型对象解析为class对象，type对象包括GenericArrayType.class, ParameterizedType.class, TypeVariable.class, WildcardType.class,Class.class

类型:工具类，提供静态方法将type泛型类型对象解析为class对象，其中包括field,method_parameter,method_return_type,class等type

实现方案:

关键技术：对目标对象生成对应的ResolvableType对象，通过ResolvableType.resolve()方法解析为class对象.(目标对象为field,method_parameter,method_return_type,class)
ResolvableType实例化时，会根据目标对象的泛型类型，进行递归解析，第二次生成新的ResolvableType后，解析的type对象为class对象

1.
```
cachedType = new ResolvableType(type, typeProvider, variableResolver, resultType.hash);
```

2.
```
private ResolvableType(Type type, @Nullable TypeProvider typeProvider,
			@Nullable VariableResolver variableResolver, @Nullable ResolvableType componentType) {

		this.type = type;
		this.typeProvider = typeProvider;
		this.variableResolver = variableResolver;
		this.componentType = componentType;
		this.hash = null;
		this.resolved = resolveClass();
	}
```


3.
```
@Nullable
	private Class<?> resolveClass() {
		if (this.type == EmptyType.INSTANCE) {
			return null;
		}
		if (this.type instanceof Class) {
			return (Class<?>) this.type;
		}
		if (this.type instanceof GenericArrayType) {
			Class<?> resolvedComponent = getComponentType().resolve();
			return (resolvedComponent != null ? Array.newInstance(resolvedComponent, 0).getClass() : null);
		}
		return resolveType().resolve();
	}

```


4.
```
ResolvableType resolveType() {
		if (this.type instanceof ParameterizedType) {
			return forType(((ParameterizedType) this.type).getRawType(), this.variableResolver);
		}
		if (this.type instanceof WildcardType) {
			Type resolved = resolveBounds(((WildcardType) this.type).getUpperBounds());
			if (resolved == null) {
				resolved = resolveBounds(((WildcardType) this.type).getLowerBounds());
			}
			return forType(resolved, this.variableResolver);
		}
		if (this.type instanceof TypeVariable) {
			TypeVariable<?> variable = (TypeVariable<?>) this.type;
			// Try default variable resolution
			if (this.variableResolver != null) {
				ResolvableType resolved = this.variableResolver.resolveVariable(variable);
				if (resolved != null) {
					return resolved;
				}
			}
			// Fallback to bounds
			return forType(resolveBounds(variable.getBounds()), this.variableResolver);
		}
		return NONE;
	}

```


5.
```
forType(type, null, variableResolver);
```


6.
```
static ResolvableType forType(
			@Nullable Type type, @Nullable TypeProvider typeProvider, @Nullable VariableResolver variableResolver) {

		if (type == null && typeProvider != null) {
			type = SerializableTypeWrapper.forTypeProvider(typeProvider);
		}
		if (type == null) {
			return NONE;
		}

		// For simple Class references, build the wrapper right away -
		// no expensive resolution necessary, so not worth caching...
		if (type instanceof Class) {
			return new ResolvableType(type, typeProvider, variableResolver, (ResolvableType) null);
		}
		...
		}

```


数据结构:
ConcurrentReferenceHashMap:softReference

优化:
缓存优化：ConcurrentReferenceHashMap

设计模式:
空模式:
public static final ResolvableType NONE = new ResolvableType(EmptyType.INSTANCE, null, null, 0);
private static final ResolvableType[] EMPTY_TYPES_ARRAY = new ResolvableType[0];

语法:
volatile
private constructor,

注解:@Nullable

Resolvable,provider






