package storemanagementsystem.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import storemanagementsystem.model.Product;
import storemanagementsystem.repository.ProductRepository;
import storemanagementsystem.exception.DuplicateProductException;
import storemanagementsystem.exception.InsufficientStockException;
import storemanagementsystem.exception.InvalidProductException;
import storemanagementsystem.exception.ProductNotFoundException;

public class ProductService {

    private final ProductRepository repository;

    public ProductService() {
        this.repository = new ProductRepository();
    }

    public void addProduct(Product product) {

        if (product == null) {
            throw new InvalidProductException(
                    "Product cannot be null."
            );
        }

        try {
            product.validate();
        } catch (IllegalArgumentException e) {
            throw new InvalidProductException(e.getMessage());
        }

        if (repository.findById(product.getId()) != null) {
            throw new DuplicateProductException(
                    "A product with ID "
                    + product.getId()
                    + " already exists."
            );
        }

        repository.save(product);
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product findProductById(int id) {

        Product product = repository.findById(id);

        if (product == null) {
            throw new ProductNotFoundException(
                    "Product with ID " + id + " not found."
            );
        }

        return product;
    }

    public void updateProduct(Product updatedProduct) {

        if (updatedProduct == null) {
            throw new InvalidProductException(
                    "Product cannot be null."
            );
        }

        try {
            updatedProduct.validate();
        } catch (IllegalArgumentException e) {
            throw new InvalidProductException(e.getMessage());
        }

        Product existingProduct
                = repository.findById(updatedProduct.getId());

        if (existingProduct == null) {
            throw new ProductNotFoundException(
                    "Product with ID "
                    + updatedProduct.getId()
                    + " not found."
            );
        }

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setQuantity(updatedProduct.getQuantity());
        existingProduct.setCategory(updatedProduct.getCategory());
    }

    public void deleteProduct(int id) {

        Product product = repository.findById(id);

        if (product == null) {
            throw new ProductNotFoundException(
                    "Product with ID "
                    + id
                    + " not found."
            );
        }

        repository.delete(product);
    }

    public List<Product> searchProductsByName(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Search keyword cannot be empty."
            );
        }

        List<Product> results = new ArrayList<>();

        for (Product product : repository.findAll()) {

            if (product.getName()
                    .toLowerCase()
                    .contains(keyword.toLowerCase())) {

                results.add(product);
            }
        }

        return results;
    }

    public List<Product> sortByPriceAscending() {

        List<Product> products = repository.findAll();

        products.sort(
                Comparator.comparingDouble(Product::getPrice)
        );

        return products;
    }

    public List<Product> sortByPriceDescending() {

        List<Product> products = repository.findAll();

        products.sort(
                Comparator.comparingDouble(Product::getPrice)
                        .reversed()
        );

        return products;
    }

    public List<Product> sortByName() {

        List<Product> products = repository.findAll();

        products.sort(
                Comparator.comparing(
                        Product::getName,
                        String.CASE_INSENSITIVE_ORDER
                )
        );

        return products;
    }

    public List<Product> getLowStockProducts(int threshold) {

        if (threshold < 0) {
            throw new IllegalArgumentException(
                    "Threshold cannot be negative."
            );
        }

        List<Product> lowStockProducts = new ArrayList<>();

        for (Product product : repository.findAll()) {

            if (product.getQuantity() <= threshold) {
                lowStockProducts.add(product);
            }
        }

        return lowStockProducts;
    }

    public void decreaseStock(int productId, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero."
            );
        }

        Product product = repository.findById(productId);

        if (product == null) {
            throw new ProductNotFoundException(
                    "Product with ID " + productId + " not found."
            );
        }

        if (product.getQuantity() < quantity) {
            throw new InsufficientStockException(
                    "Insufficient stock for product: "
                    + product.getName()
            );
        }

        product.setQuantity(
                product.getQuantity() - quantity
        );
    }

    public void increaseStock(int productId, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero."
            );
        }

        Product product = repository.findById(productId);

        if (product == null) {
            throw new ProductNotFoundException(
                    "Product with ID " + productId + " not found."
            );
        }

        product.setQuantity(
                product.getQuantity() + quantity
        );
    }
}
