package cglib;

public class DelegateClass {
    public DelegateClass() {
    }

    public DelegateClass(String string) {
    }

    public boolean add(String string, int i) {
        System.out.println("This is add method: " + string + ", " + i);
        return true;
    }

    public void update() {
        System.out.println("This is update method");
    }
}
