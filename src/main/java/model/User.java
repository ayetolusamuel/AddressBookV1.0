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

    public User(String id, String name, String email, String phone_number, String resident_address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
        this.resident_address = resident_address;
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
