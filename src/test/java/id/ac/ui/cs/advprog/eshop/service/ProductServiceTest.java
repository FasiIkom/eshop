package id.ac.ui.cs.advprog.eshop.service;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import id.ac.ui.cs.advprog.eshop.model.Product;

class ProductServiceTest {
    
    @Mock
    private Product product;
    
    @Mock
    private List<Product> productList;
    
    @InjectMocks
    private ProductService productService = new ProductService() {
        @Override
        public Product create(Product product) {
            return product;
        }

        @Override
        public List<Product> findAll() {
            return productList;
        }

        @Override
        public Product findById(String id) {
            return product;
        }

        @Override
        public void update(Product product) {
            // Mock implementation
        }

        @Override
        public void delete(Product product) {
            // Mock implementation
        }
    };

    @Test
    void testCreate() {
        Product result = productService.create(product);
        assertEquals(product, result);
    }

    @Test
    void testFindAll() {
        List<Product> result = productService.findAll();
        assertEquals(productList, result);
    }

    @Test
    void testFindById() {
        String id = "testId";
        Product result = productService.findById(id);
        assertEquals(product, result);
    }

    @Test
    void testUpdate() {
        assertDoesNotThrow(() -> productService.update(product));
    }

    @Test
    void testDelete() {
        assertDoesNotThrow(() -> productService.delete(product));
    }
}