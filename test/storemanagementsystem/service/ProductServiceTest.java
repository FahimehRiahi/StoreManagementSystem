package storemanagementsystem.service;

import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import storemanagementsystem.exception.DuplicateProductException;
import storemanagementsystem.exception.InvalidProductException;
import storemanagementsystem.exception.ProductNotFoundException;
import storemanagementsystem.exception.InsufficientStockException;
import storemanagementsystem.model.Product;

public class ProductServiceTest {

    @Test
    public void shouldAddProduct() {

        ProductService service = new ProductService();

        Product product = new Product(
                1,
                "Laptop",
                45000000,
                10,
                "Electronics"
        );

        service.addProduct(product);

        assertEquals(
                1,
                service.getAllProducts().size()
        );
    }

    @Test
    public void shouldRejectDuplicateId() {
        ProductService service = new ProductService();

        Product product1 = new Product(
                1,
                "Laptop",
                45000000,
                10,
                "Electronics"
        );

        Product product2 = new Product(
                1,
                "Mouse",
                500000,
                20,
                "Accessories"
        );

        service.addProduct(product1);

        try {
            service.addProduct(product2);
            fail("Expected DuplicateProductException");
        } catch (DuplicateProductException e) {
            // Expected
        }
    }

    @Test
    public void shouldUpdateProduct() {
        ProductService service = new ProductService();

        Product product = new Product(
                1,
                "Laptop",
                45000000,
                10,
                "Electronics"
        );

        service.addProduct(product);

        Product updatedProduct = new Product(
                1,
                "Gaming Laptop",
                50000000,
                8,
                "Electronics"
        );

        service.updateProduct(updatedProduct);

        Product result = service.findProductById(1);

        assertEquals("Gaming Laptop", result.getName());
        assertEquals(50000000, result.getPrice(), 0.01);
        assertEquals(8, result.getQuantity());
    }

    @Test
    public void shouldDeleteProduct() {
        ProductService service = new ProductService();

        Product product = new Product(
                1,
                "Laptop",
                45000000,
                10,
                "Electronics"
        );

        service.addProduct(product);

        service.deleteProduct(1);

        try {
            service.findProductById(1);
            fail("Expected ProductNotFoundException");
        } catch (ProductNotFoundException e) {
            // Expected
        }
    }

    @Test
    public void shouldFindProductById() {

        ProductService service = new ProductService();

        Product product = new Product(
                1,
                "Laptop",
                45000000,
                10,
                "Electronics"
        );

        service.addProduct(product);

        Product result = service.findProductById(1);

        assertEquals(1, result.getId());
        assertEquals("Laptop", result.getName());
        assertEquals(45000000, result.getPrice(), 0.01);
        assertEquals(10, result.getQuantity());
    }

    @Test
    public void shouldThrowExceptionWhenProductNotFound() {

        ProductService service = new ProductService();

        try {
            service.findProductById(999);
            fail("Expected ProductNotFoundException");
        } catch (ProductNotFoundException e) {
            // Expected
        }
    }

    @Test
    public void shouldRejectNegativePrice() {

        ProductService service = new ProductService();

        Product product = new Product(
                10,
                "Invalid Laptop",
                -1000,
                5,
                "Electronics"
        );

        try {
            service.addProduct(product);
            fail("Expected InvalidProductException");
        } catch (InvalidProductException e) {
            // Expected
        }
    }

    @Test
    public void shouldRejectNegativeQuantity() {

        ProductService service = new ProductService();

        Product product = new Product(
                11,
                "Invalid Mouse",
                500000,
                -5,
                "Accessories"
        );

        try {
            service.addProduct(product);
            fail("Expected InvalidProductException");
        } catch (InvalidProductException e) {
            // Expected
        }
    }

    @Test
    public void shouldDecreaseStockAfterSale() {

        ProductService service = new ProductService();

        Product product = new Product(
                1,
                "Laptop",
                45000000,
                10,
                "Electronics"
        );

        service.addProduct(product);

        service.decreaseStock(1, 3);

        Product result = service.findProductById(1);

        assertEquals(7, result.getQuantity());
    }

    @Test
    public void shouldRejectSaleWhenStockIsInsufficient() {

        ProductService service = new ProductService();

        Product product = new Product(
                1,
                "Laptop",
                45000000,
                5,
                "Electronics"
        );

        service.addProduct(product);

        try {
            service.decreaseStock(1, 10);
            fail("Expected InsufficientStockException");
        } catch (InsufficientStockException e) {
            // Expected
        }

        // موجودی نباید بعد از فروش ناموفق تغییر کند.
        assertEquals(5, service.findProductById(1).getQuantity());
    }

    @Test
    public void shouldIncreaseStock() {

        ProductService service = new ProductService();

        Product product = new Product(
                1,
                "Laptop",
                45000000,
                5,
                "Electronics"
        );

        service.addProduct(product);

        service.increaseStock(1, 10);

        Product result = service.findProductById(1);

        assertEquals(15, result.getQuantity());
    }

    @Test
    public void shouldSortProductsByPriceAscending() {
        ProductService service = new ProductService();

        service.addProduct(
                new Product(1, "Laptop", 45000000, 5, "Electronics")
        );
        service.addProduct(
                new Product(2, "Mouse", 500000, 10, "Accessories")
        );
        service.addProduct(
                new Product(3, "Monitor", 8000000, 3, "Electronics")
        );

        List<Product> products = service.sortByPriceAscending();

        assertEquals("Mouse", products.get(0).getName());
        assertEquals("Monitor", products.get(1).getName());
        assertEquals("Laptop", products.get(2).getName());
    }

    @Test
    public void shouldSortProductsByPriceDescending() {
        ProductService service = new ProductService();

        service.addProduct(
                new Product(1, "Laptop", 45000000, 5, "Electronics")
        );
        service.addProduct(
                new Product(2, "Mouse", 500000, 10, "Accessories")
        );
        service.addProduct(
                new Product(3, "Monitor", 8000000, 3, "Electronics")
        );

        List<Product> products = service.sortByPriceDescending();

        assertEquals("Laptop", products.get(0).getName());
        assertEquals("Monitor", products.get(1).getName());
        assertEquals("Mouse", products.get(2).getName());
    }

    @Test
    public void shouldSortProductsByName() {
        ProductService service = new ProductService();

        service.addProduct(
                new Product(1, "Mouse", 500000, 10, "Accessories")
        );
        service.addProduct(
                new Product(2, "Laptop", 45000000, 5, "Electronics")
        );
        service.addProduct(
                new Product(3, "Monitor", 8000000, 3, "Electronics")
        );

        List<Product> products = service.sortByName();

        assertEquals("Laptop", products.get(0).getName());
        assertEquals("Monitor", products.get(1).getName());
        assertEquals("Mouse", products.get(2).getName());
    }

    @Test
    public void shouldReturnProductsWithLowStock() {
        ProductService service = new ProductService();

        service.addProduct(
                new Product(1, "Laptop", 45000000, 10, "Electronics")
        );
        service.addProduct(
                new Product(2, "Mouse", 500000, 2, "Accessories")
        );
        service.addProduct(
                new Product(3, "Monitor", 8000000, 5, "Electronics")
        );

        List<Product> products = service.getLowStockProducts(5);

        assertEquals(2, products.size());
        assertEquals("Mouse", products.get(0).getName());
        assertEquals("Monitor", products.get(1).getName());
    }

    @Test
    public void shouldRejectZeroQuantityWhenDecreasingStock() {
        ProductService service = new ProductService();

        service.addProduct(
                new Product(1, "Laptop", 45000000, 10, "Electronics")
        );

        try {
            service.decreaseStock(1, 0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        assertEquals(10, service.findProductById(1).getQuantity());
    }

    @Test
    public void shouldRejectNegativeQuantityWhenIncreasingStock() {
        ProductService service = new ProductService();

        service.addProduct(
                new Product(1, "Laptop", 45000000, 10, "Electronics")
        );

        try {
            service.increaseStock(1, -5);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        assertEquals(10, service.findProductById(1).getQuantity());
    }

    @Test
    public void shouldRejectStockDecreaseForNonExistingProduct() {
        ProductService service = new ProductService();

        try {
            service.decreaseStock(999, 1);
            fail("Expected ProductNotFoundException");
        } catch (ProductNotFoundException e) {
            // Expected
        }
    }
}
