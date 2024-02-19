package test;

import dao.CategoryDao;
import dao.ManufacturerDao;
import dao.ProductDao;
import dao.SaleDao;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import persistance.ConnectionPool;
import persistance.DbInitialization;
import persistance.models.Manufacturer;
import persistance.models.Product;

public class Main {

    public static void main(String[] args) {
        try {
            DbInitialization.apply();

            Connection con = ConnectionPool.getConnection();
            CategoryDao categoryDao = CategoryDao.getInstance();
            SaleDao saleDao = SaleDao.getInstance();
            ManufacturerDao manufacturerDao = ManufacturerDao.getInstance();
            ProductDao productDao = ProductDao.getInstance();


            Manufacturer manufacturer = new Manufacturer("Adidas","Germany");
            manufacturerDao.save(manufacturer);


            Optional<Product> opt = productDao.findOneById(2);
            if(opt.isPresent()){
                Product product = opt.get();
                product.setManufacturer(manufacturer);
                productDao.update(product);
            }


            for(Product pr:productDao.findAll()){
                System.out.println(pr.getId() + "\t" + pr.getName() + "\t" + pr.getCategory().getName() + "\t"+
                    pr.getManufacturer().getName());
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}