package swagger.swagger.model;

/**
 * @author ganchaoyang
 * @date 2019/3/1013:55
 */
public class User2 {
    
    /**
     * 用户Id
     */
    private int id;
    
    /**
     * 用户名
     */
    private String name;
    
    
    /**
     * 用户地址
     */
    private String address;
    
    public int getId() {
        return id;
    }
    
    public User2 setId(int id) {
        this.id = id;
        return this;
    }
    
    public String getName() {
        return name;
    }
    
    public User2 setName(String name) {
        this.name = name;
        return this;
    }
    
    public String getAddress() {
        return address;
    }
    
    public User2 setAddress(String address) {
        this.address = address;
        return this;
    }
}
