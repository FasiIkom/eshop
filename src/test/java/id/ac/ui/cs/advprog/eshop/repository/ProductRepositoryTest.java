package id.ac.ui.cs.advprog.eshop.repository;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import id.ac.ui.cs.advprog.eshop.model.Product;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductName(), savedProduct.getProductName());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditProductPositive() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Coba mengedit produk dengan id sama
        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Sampo Cap Bambang Edisi Baru");
        updatedProduct.setProductQuantity(150);
        productRepository.update(updatedProduct);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();

        // Cek apakah nama atau quantity produk telah teredit
        assertEquals(updatedProduct.getProductId(), savedProduct.getProductId());
        assertEquals(updatedProduct.getProductName(), savedProduct.getProductName());
        assertEquals(updatedProduct.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testEditProductNegative() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("invalid-id");
        updatedProduct.setProductName("Produk Tidak Ada");
        updatedProduct.setProductQuantity(10);

        // Coba mengedit produk yang tidak ada
        productRepository.update(updatedProduct);
        // Repository tetap kosong karena produk tidak ditemukan
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteProductPositive() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        productRepository.delete(product);

        Product deletedProduct = productRepository.findById(product.getProductId());
        assertNull(deletedProduct);
    }


    
    @Test
    void testDeleteProductNegative() {
        String nonExistentProductId = "non-existent-id";
        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId(nonExistentProductId);
        productRepository.delete(nonExistentProduct);
        Product deletedProduct = productRepository.findById(nonExistentProductId);
        assertNull(deletedProduct);
    }

    @Test
    void testFindByIdWithMultipleProducts() {
        // Setup first product
        Product product1 = new Product();
        product1.setProductId("123");
        product1.setProductName("Product 1");
        product1.setProductQuantity(1);
        productRepository.create(product1);

        // Setup second product
        Product product2 = new Product();
        product2.setProductId("456");
        product2.setProductName("Product 2");
        product2.setProductQuantity(2);
        productRepository.create(product2);

        // Test finding existing products
        Product found1 = productRepository.findById("123");
        Product found2 = productRepository.findById("456");
        Product notFound = productRepository.findById("789");

        // Assertions
        assertNotNull(found1);
        assertEquals("123", found1.getProductId());
        assertEquals("Product 1", found1.getProductName());
        
        assertNotNull(found2);
        assertEquals("456", found2.getProductId());
        assertEquals("Product 2", found2.getProductName());
        
        assertNull(notFound);
    }

    @Test
    void testUpdateProductWithMultipleProducts() {
        // Create first product
        Product product1 = new Product();
        product1.setProductId("111");
        product1.setProductName("Product 1");
        product1.setProductQuantity(1);
        productRepository.create(product1);

        // Create second product
        Product product2 = new Product();
        product2.setProductId("222");
        product2.setProductName("Product 2");
        product2.setProductQuantity(2);
        productRepository.create(product2);

        // Create third product
        Product product3 = new Product();
        product3.setProductId("333");
        product3.setProductName("Product 3");
        product3.setProductQuantity(3);
        productRepository.create(product3);

        // Update middle product (product2)
        Product updatedProduct = new Product();
        updatedProduct.setProductId("222");  // same ID as product2
        updatedProduct.setProductName("Updated Product 2");
        updatedProduct.setProductQuantity(20);
        productRepository.update(updatedProduct);

        // Verify the update
        Product found = productRepository.findById("222");
        assertNotNull(found);
        assertEquals("Updated Product 2", found.getProductName());
        assertEquals(20, found.getProductQuantity());

        // Verify other products remain unchanged
        Product found1 = productRepository.findById("111");
        assertEquals("Product 1", found1.getProductName());
        
        Product found3 = productRepository.findById("333");
        assertEquals("Product 3", found3.getProductName());
    }
}