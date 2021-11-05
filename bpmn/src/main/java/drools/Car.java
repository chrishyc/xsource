package drools;

public class Car {
    private int discount = 100;
    private Person person;
    
    public int getDiscount() {
        return discount;
    }
    
    public void setDiscount(int discount) {
        this.discount = discount;
    }
    
    public Person getPerson() {
        return person;
    }
    
    public void setPerson(Person person) {
        this.person = person;
    }
}
