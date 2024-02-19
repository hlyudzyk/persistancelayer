package persistance.models;

public class Category extends Model {
    private String name;

    public Category(String name) {
        this.name = name;
    }
    public Category(int id, String name) {
        this.setId(id);
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
