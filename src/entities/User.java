package entities;

public class User extends Entity {
    private final String SALT = "d131dd02c5e6eec4";
    
    private String name;
    private String password;
    private boolean isAdmin;
    private boolean isActive;

    public User(long id, String name, String password, boolean isAdmin) {
        super(id);
        this.name = name;
        this.password = password;
        this.isAdmin = isAdmin;
        this.isActive = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

}