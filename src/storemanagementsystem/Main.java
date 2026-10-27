package storemanagementsystem;

import java.util.List;
import storemanagementsystem.exception.DuplicateProductException;
import storemanagementsystem.exception.InvalidProductException;
import storemanagementsystem.exception.ProductNotFoundException;
import storemanagementsystem.model.Product;
import storemanagementsystem.service.ProductService;

public class Main {

    public static void main(String[] args) {

        ProductService service = new ProductService();

        Product laptop = new Product(
                1,
                "Laptop",
                45000000,
                8,
                "Electronics"
        );

        Product mouse = new Product(
                2,
                "Mouse",
                500000,
                20,
                "Accessories"
        );

        service.addProduct(laptop);
        service.addProduct(mouse);

        System.out.println("=== PRODUCTS ===");
        printProducts(service.getAllProducts());

        // Test duplicate ID
        try {

            Product duplicate = new Product(
                    1,
                    "Keyboard",
                    1200000,
                    5,
                    "Accessories"
            );

            service.addProduct(duplicate);

        } catch (DuplicateProductException e) {

            System.out.println(
                    "Duplicate Error: " + e.getMessage()
            );
        }

        // Test invalid product
        try {

            Product invalid = new Product(
                    3,
                    "",
                    -100,
                    -5,
                    ""
            );

            service.addProduct(invalid);

        } catch (InvalidProductException e) {

            System.out.println(
                    "Validation Error: " + e.getMessage()
            );
        }

        // Test product not found
        try {

            service.deleteProduct(99);

        } catch (ProductNotFoundException e) {

            System.out.println(
                    "Not Found Error: " + e.getMessage()
            );
        }

        // Test sorting
        System.out.println("\n=== SORT BY PRICE ===");

        List<Product> sortedProducts
                = service.sortByPriceAscending();

        printProducts(sortedProducts);

    }

    private static void printProducts(List<Product> products) {

        for (Product product : products) {
            System.out.println(product);
        }
    }
}
