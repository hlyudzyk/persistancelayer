package persistance.models;

import java.time.LocalDate;

public class Sale extends Model{
    private Product product;
    private Client client;
    private int quantity;
    private LocalDate date;

    public Sale(Product product, Client client, int quantity, LocalDate date) {
        this.product = product;
        this.client = client;
        this.quantity = quantity;
        this.date = date;
    }

    public Sale(int id,Product product, Client client, int quantity, LocalDate date) {
        this.setId(id);
        this.product = product;
        this.client = client;
        this.quantity = quantity;
        this.date = date;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
