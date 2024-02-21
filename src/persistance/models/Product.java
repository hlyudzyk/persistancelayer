package persistance.models;

public class Product extends Model {
    private String name;
    private Category category;
    private Manufacturer manufacturer;
    private double price;

    private Product(ProductBuilder productBuilder) {
        this.setId(productBuilder.id);
        this.name = productBuilder.name;
        this.category = productBuilder.category;
        this.manufacturer = productBuilder.manufacturer;
        this.price = productBuilder.price;
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

    public static class ProductBuilder {
        private int id;
        private String name;
        private Category category;
        private Manufacturer manufacturer;
        private double price;

        public ProductBuilder() {}

        public ProductBuilder id(int id) {
            this.id = id;
            return this;
        }

        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder category(Category category) {
            this.category = category;
            return this;
        }

        public ProductBuilder manufacturer(Manufacturer manufacturer) {
            this.manufacturer = manufacturer;
            return this;
        }

        public ProductBuilder price(double price) {
            this.price = price;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
