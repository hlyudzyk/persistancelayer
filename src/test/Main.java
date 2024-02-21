package test;

import exception.ConnectionException;
import persistance.dao.CategoryDao;
import persistance.dao.ManufacturerDao;
import persistance.dao.ProductDao;
import persistance.dao.SaleDao;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import persistance.ConnectionPool;
import persistance.DbInitialization;
import persistance.models.Manufacturer;
import persistance.models.Manufacturer.ManufacturerBuilder;
import persistance.models.Product;

public class Main {

    public static void main(String[] args) {
        try {
            DbInitialization.apply();

            Connection con = ConnectionPool.get();
            CategoryDao categoryDao = CategoryDao.getInstance();
            SaleDao saleDao = SaleDao.getInstance();
            ManufacturerDao manufacturerDao = ManufacturerDao.getInstance();
            ProductDao productDao = ProductDao.getInstance();


            Manufacturer manufacturer = new ManufacturerBuilder()
            .name("Puma").country("Germany").build();
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
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}