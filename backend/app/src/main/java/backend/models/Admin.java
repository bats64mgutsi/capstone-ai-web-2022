package backend.models;

public class Admin {
    public String username;
    public String hashedPassword;

    public Admin(String username, String hashedPassword)
    {
        this.hashedPassword=hashedPassword;
        this.username=username;
    }
}
