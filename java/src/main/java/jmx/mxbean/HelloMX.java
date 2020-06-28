package jmx.mxbean;

public class HelloMX implements HelloMXBean{
    public void sayHello() {
        System.out.println("hello, world");
    }
    
    public int add(int x, int y) {
        return x + y;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getCacheSize() {
        return this.cacheSize;
    }
    
    public synchronized void setCacheSize(int size) {
        
        this.cacheSize = size;
        System.out.println("Cache size now " + this.cacheSize);
    }
    
    @Override
    public Book getBook() {
        return book;
    }
    
    @Override
    public void addBook(Book book) {
        this.book = book;
    }
    
    private final String name = "Reginald";
    private int cacheSize = DEFAULT_CACHE_SIZE;
    private static final int
            DEFAULT_CACHE_SIZE = 200;
    private Book book;
}
