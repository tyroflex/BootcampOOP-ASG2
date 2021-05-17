package javaClasses;
import java.util.Comparator;

public class User {

    private Integer number;
    private final String username;

    public User(Integer number, String username) {
        this.number = number;
        this.username = username;
    }

    public Integer getNumber() {
        return number;
    }

    public String getUsername() {
        return username;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
