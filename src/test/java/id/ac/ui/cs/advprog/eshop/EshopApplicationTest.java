package id.ac.ui.cs.advprog.eshop;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

class EshopApplicationTest {

    @Test
    void testMainMethod() {
        EshopApplication.main(new String[]{});
        assertNotNull(EshopApplication.class);
    }
}