package model;
/*
model does not have access to view..
 */
public class User {
    private String id;
    private String name;
    private String email;
    private String phone_number;
    private String resident_address;
    private String state;
    private String age;
    private String gender;
    private String salary;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public User(String id, String name, String email, String phone_number, String resident_address, String state,
                String age, String gender, String salary) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
        this.resident_address = resident_address;
        this.state = state;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getResident_address() {
        return resident_address;
    }

    public void setResident_address(String resident_address) {
        this.resident_address = resident_address;
    }
}
