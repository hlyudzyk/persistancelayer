package persistance.models;

public class Client extends Model{
    private String name;
    private String email;



    public Client(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Client(int id, String name, String email) {
        this.setId(id);
        this.name = name;
        this.email = email;
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
}
