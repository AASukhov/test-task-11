package task.entity;

import task.entity.enums.Role;
import task.entity.enums.Status;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    private int id;
    private String name;
    private String email;
    private String password;
    private double balance;
    private String role;

    public User (){}

    public User(int id, String name, String email, String password, double balance) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = balance;
    }

    public User(int id, String name, String email, String password, double balance, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.role = role;
    }

    public User(String name, String email, String password, double balance) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = balance;
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
