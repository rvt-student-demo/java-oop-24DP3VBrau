package rvt;

public class Teacher extends Person {
    public String name;
    public String address;
    public String pk;
    public String email;
    public String darbalaiks;

    public Teacher(String name, String address) {
        this.name = name;
        this.address = address;
    }
    public String getName() {
        return name;
    }


}
