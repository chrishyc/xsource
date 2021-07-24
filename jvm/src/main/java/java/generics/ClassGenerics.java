package java.generics;


import java.util.List;
import java.util.Set;

public class ClassGenerics<T> {
    T item;
    Class<?> clazz;
    Class<T> clazzT;

    public void setData(T t) {
        this.item = t;
    }

    public T getData() {
        return this.item;
    }

    public void setClazz(Class<?> clazz) {

    }

    public void setClazzT(Class<T> clazz) {

    }

    /**
     * 返回值前面的<E>申明方法泛型和泛型变量E
     * 泛型类型由输入参数中的E和返回类型的E综合确定
     * @param param
     * @param <E>
     * @return
     */
    public <E> E getAge(List<E> param) {
        return param.get(0);
    }

    public <K, V> V getAge(K param, V v) {
        V a = null;
        return a;
    }

    /**
     * T的类型取决于参数还是返回值？
     * The inference algorithm determines the types of the arguments and,
     * if available, the type that the result is being assigned, or returned
     *
     * @param a1
     * @param a2
     * @param <T>
     * @return
     */
    static <T> T pick(T a1, T a2) {
        return a1;
    }

    /**
     * 用 extends 关键字声明，表示参数化的类型可能是所指定的类型，或者是此类型的子类。
     *
     * @param animals
     * @return
     */
    static <E> int countLegs(List<? extends E> animals) {
        int retVal = 0;
        return retVal;
    }

    static <E> int countLegs2(List<? extends Animal> animals) {
        int retVal = 0;
        return retVal;
    }

    static int countLegs3(List<Animal> animals) {
        int retVal = 0;
        return retVal;
    }

    static int countLegs4(List<?> animals) {
        int retVal = 0;
        return retVal;
    }

    /**
     * ?=object
     *
     * @param set
     */
    public void getName(Set<Integer> set) {
        return;
    }

    // 这不是一个泛型方法，这就是一个普通的方法，只是使用了Generic<Number>这个泛型类做形参而已。
    public void showKeyValue1(Generics<Number> obj) {
    }

    //这也不是一个泛型方法，这也是一个普通的方法，只不过使用了泛型通配符?
    //同时这也印证了泛型通配符章节所描述的，?是一种类型实参，可以看做为Number等所有类的父类
    public void showKeyValue2(Generics<?> obj) {
    }

    public void showkey(T genericObj) {

    }

}
