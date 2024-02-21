package persistance.models;

import java.time.LocalDate;

public class Sale extends Model {
    private Product product;
    private Client client;
    private int quantity;
    private LocalDate date;

    private Sale(SaleBuilder saleBuilder) {
        this.setId(saleBuilder.id);
        this.product = saleBuilder.product;
        this.client = saleBuilder.client;
        this.quantity = saleBuilder.quantity;
        this.date = saleBuilder.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Product getProduct() {
        return product;
    }

    public Client getClient() {
        return client;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDate getDate() {
        return date;
    }

    public static class SaleBuilder {
        private int id;
        private Product product;
        private Client client;
        private int quantity;
        private LocalDate date;

        public SaleBuilder() {}

        public SaleBuilder id(int id) {
            this.id = id;
            return this;
        }

        public SaleBuilder product(Product product) {
            this.product = product;
            return this;
        }

        public SaleBuilder client(Client client) {
            this.client = client;
            return this;
        }

        public SaleBuilder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public SaleBuilder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Sale build() {
            return new Sale(this);
        }
    }
}
