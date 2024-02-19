package persistance.models;

public class Product extends Model {
    private String name;
    private Category category;
    private Manufacturer manufacturer;
    private double price;

    public Product(String name, Category category, Manufacturer manufacturer, double price) {
        this.name = name;
        this.category = category;
        this.manufacturer = manufacturer;
        this.price = price;
    }

    public Product(int id,String name, Category category, Manufacturer manufacturer, double price) {
        this.setId(id);
        this.name = name;
        this.category = category;
        this.manufacturer = manufacturer;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
