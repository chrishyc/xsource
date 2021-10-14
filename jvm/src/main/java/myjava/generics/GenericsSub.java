package myjava.generics;

public class GenericsSub extends Generics<String> {
    @Override
    public String getT(String args) {
        return args;
    }
}
