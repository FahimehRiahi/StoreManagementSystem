package storemanagementsystem.repository;

import java.util.ArrayList;
import java.util.List;
import storemanagementsystem.model.Product;

public class ProductRepository {

    private final List<Product> products = new ArrayList<>();

    public void save(Product product) {
        products.add(product);
    }

    public List<Product> findAll() {
        return new ArrayList<>(products);
    }

    public Product findById(int id) {

        for (Product product : products) {

            if (product.getId() == id) {
                return product;
            }
        }

        return null;
    }

    public void delete(Product product) {
        products.remove(product);
    }
}
