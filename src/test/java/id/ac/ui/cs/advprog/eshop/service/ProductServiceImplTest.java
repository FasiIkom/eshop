package id.ac.ui.cs.advprog.eshop.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
    }

    @Test
    void testCreate() {
        when(productRepository.create(product)).thenReturn(product);

        Product created = productService.create(product);

        verify(productRepository).create(product);
        assertNotNull(created);
        assertEquals(product.getProductId(), created.getProductId());
        assertEquals(product.getProductName(), created.getProductName());
        assertEquals(product.getProductQuantity(), created.getProductQuantity());
    }

    @Test
    void testFindAll() {
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        Iterator<Product> iterator = productList.iterator();

        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> products = productService.findAll();

        verify(productRepository).findAll();
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        assertEquals(product, products.get(0));
    }

    @Test
    void testFindById() {
        when(productRepository.findById(product.getProductId())).thenReturn(product);

        Product found = productService.findById(product.getProductId());

        verify(productRepository).findById(product.getProductId());
        assertNotNull(found);
        assertEquals(product.getProductId(), found.getProductId());
    }

    @Test
    void testUpdate() {
        productService.update(product);
        verify(productRepository).update(product);
    }

    @Test
    void testDelete() {
        productService.delete(product);
        verify(productRepository).delete(product);
    }
}