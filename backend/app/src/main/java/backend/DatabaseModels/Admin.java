package backend.DatabaseModels;

public class Admin {
    public String username;
    public String hashedPassword;
    public int id;

    public Admin(int id, String username, String hashedPassword)
    {
        this.id = id;
        this.hashedPassword=hashedPassword;
        this.username=username;
    }
}
